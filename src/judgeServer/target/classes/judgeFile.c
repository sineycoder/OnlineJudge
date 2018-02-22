#include<stdio.h> 
#include<stdlib.h> 
#include<string.h> 
#include<unistd.h> 
#include<seccomp.h> 
#include<time.h> 
#include<signal.h> 
#include<sys/time.h>
#include<sys/types.h> 
#include <sys/resource.h> 
#include<wait.h>
int fileno(FILE *stream);
void setSeccomp(){ 
	scmp_filter_ctx ctx; 
	ctx = seccomp_init(SCMP_ACT_KILL); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(execve), 0);//limit execve 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(brk), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(mmap), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(access), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(open), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(close), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(fstat), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(read), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(mprotect), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(munmap), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(write), 1,SCMP_A0(SCMP_CMP_LE,2));//limit write 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(exit_group), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(arch_prctl), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(prctl), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(rt_sigaction), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(getrlimit), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(setrlimit), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(getpid), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(wait4), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(tgkill), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(setuid), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(dup2), 0); 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(setitimer), 0); 
 
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(lseek), 0); 
	if(seccomp_load(ctx)){ 
		printf("error\n"); 
	} 
} 
void setRlimit(long memories){ 
	/*struct rlimit time_limit; //cpu 运行时间限制，精确到s
	time_limit.rlim_cur=1; 
	time_limit.rlim_max=1; 
	setrlimit(RLIMIT_CPU,&time_limit);*/
	struct rlimit memory_limit; //内存限制，精确到字节,内存限制比较有难度
	memory_limit.rlim_cur=memories*1024; 
	memory_limit.rlim_max=memories*1024; 
	setrlimit(RLIMIT_AS,&memory_limit); 
} 
void setIlimit(long sec,long usec,int cpu_time){ 
	struct itimerval time_val; 
	time_val.it_interval.tv_sec=time_val.it_interval.tv_usec=0; 
	time_val.it_value.tv_sec=sec; 
	time_val.it_value.tv_usec=usec*1000; 
	int res = setitimer(cpu_time?ITIMER_VIRTUAL:ITIMER_REAL, &time_val, NULL); 
} 

int main(int args,char *argv[],char *envp[]){ 
	 //函数声明
	pid_t wait4(pid_t pid, int *status, int options,struct rusage *rusage);
	time_t nowTime;//truly time
	long t = time(&nowTime); 
	pid_t pid; 
	pid = fork(); 
	if(!pid){
		int i,step=6;
		long cpu_time_second = atol(argv[2]),cpu_time_microsecond = atol(argv[3]),memories = atol(argv[4]);//参数分别传入并转为long	
		char *argvs[15];
		for(i = step;i<args-3;i++){
			argvs[i-step] = argv[i];
		}
		argvs[i-step] = NULL;

		if(!strcmp("1",argv[1]))
			setSeccomp();//seccompsandbox
		
		if(memories != -1)
			setRlimit(memories);//set memory 
		setIlimit(cpu_time_second,cpu_time_microsecond,1);//set cpu time 
		setIlimit(2*(cpu_time_second+1),0,0);//set truly time
		
		//input redirect
		if(strcmp("NULL",argv[args-3]) && dup2(fileno(fopen(argv[args-3],"r")),0)==-1){//if param is NULL,there is no input file
                       printf("redirect error\n");
                }
		
		//output redirect
		if(strcmp("NULL",argv[args-2]) && dup2(fileno(fopen(argv[args-2],"w")),1)==-1){ 
	        	printf("redirect error\n"); 
	  	}
		
		//error redirect 
	 	if(dup2(fileno(fopen(argv[args-1],"w")),fileno(stderr))==-1){ 
	        	printf("error"); 
	 	}
		
		
		int tt = setuid(1000);//set uid limit permission
		//char source[] = command;//"/home/oj_judger_server/judger_server1/Main" 
		//char *argvs[] = {command,NULL};
		execve(argv[step-1],argvs,envp);

		exit(1); //if return ,error
	}else{ 
		 //监听子进程
		struct rusage rus; 
		int status=0,signal=0; 
		wait4(pid,&status,0,&rus); 
		double cpu_time = (double)(rus.ru_utime.tv_sec+rus.ru_stime.tv_sec)*1000+(double)(rus.ru_utime.tv_usec+rus.ru_stime.tv_usec)/1000;//计算cpu时间
		double real_time = (double)(time(&nowTime)-t);//计算真实时间
		int memories = rus.ru_maxrss;//计算内存
		
		if(WIFSIGNALED(status))
			signal = WTERMSIG(status);
			
		printf("status=%d\n",WEXITSTATUS(status));
		printf("signal=%d\n",signal);
		printf("cpu_time=%lf\n",cpu_time);
		printf("real_time=%lf\n",real_time);
		printf("memories=%d\n",memories);

	} 
	return 0; 
}
