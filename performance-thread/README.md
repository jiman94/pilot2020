# open files, max user processes

http://localhost:8080/connect-hang?index=aaa

Linux
open files, max user processes 설정

## 1. Max user processes

Linux에는 OS 레벨에서의 제한 설정이 있습니다.  
(user limit) 이란 명령어로 확인  

  * soft ulimit

ulimit -a

* hard ulimit

ulimit -aH
  

톰캣을 이용해서 서버 운영 도중, 
다음과 같이 ```OutOfMemoryError```가 발생했다고 가정하겠습니다.

더이상 쓰레드를 생성할 수 없다는 에러인데요.  


기본 설정이 ```open files```가 1024, ```max user processes```가 3902
현재 설정에서 ```1024 <= 동시에 생성가능한 쓰레드수 <= 3902```라면 ```max user processes```

  
  
코드

@GetMapping("/1100")

호출

http://localhost:8080/4000


HTTP 요청이 오면 **비동기로 4천개의 쓰레드를 동시에 생성**하고, 20분간 유지합니다.




curl -i 
tail -f nohup



사진속을 보시면 3855번째에서 ```unable to create new native thread``` 에러 메세지가 발생했습니다.  

즉, open file 제한인 1024개를 초과해서 쓰레드가 생성된 것입니다!  

**max user processes만큼만 쓰레드가 생성**된것을 확인할 수 있습니다.



## 2. Open files

두번째로 위에 있던 open files 값은 어떤 값을 가리키는지 알아보겠습니다.  

 **프로세스가 가질 수 있는 소켓 포함 파일 개수**를 나타낸다는 것을 알 수 있습니다.  
  

* RestTemplate로 다른 서버로 API요청 (소켓 생성)을 보내고, 해당 서버에서는 20분간 응답을 대기 시킵니다.

* 위 요청을 동시에 1100개를 보냅니다.

* open files의 제한이 1024개이기 때문에 1100개가 발송되기전에 open files 관련된 에러가 발생하면 실험 성공!
* Java, Spring을 실행시키면 기본적으로 몇개의 file이 오픈됩니다. 
* 이를 계산하면서 하기가 애매하니, 1100개를 보내면 기본 open된 파일 + 추가 생성된 파일을 합쳐서 1024개가 넘어가는 시점에 에러가 발생한다는 계산입니다.


API 요청을 받아, 20분간 대기시켜줄 서버로 ec2를 한대 더 생성합니다.  
  
테스트에 사용한 코드는 다음과 같습니다.  
  
**요청을 보낼 메소드**

* RestTemplate 타임아웃이 먼저 나면 안되기 때문에 타임아웃을 30분으로 지정합니다.

**요청을 받을 메소드**

자 그리고 **동시에 1100개의 요청**을 보낼수 있도록 간단한 비동기 요청 스크립트를 만듭니다.


단순 쓰레드 생성과 달리, **EC2의 서버 메모리가 먼저 부족**해져서 EC2 사양을 높여서 다시 실험하겠습니다.  


 memory  (8GB) 

> AWS EC2의 사양을 올릴수록 ```max user processes```가 적절한 값으로 증가하는 것을 확인할 수 있습니다.  
이를 통해 알 수 있는 것은, AWS EC2를 사용하실 경우 ```max user processes``` AWS 내부에서 인스턴스 사양에 맞게 적절한 값을 세팅해주니, 굳이 저희가 손댈필요는 없다는 것입니다.

자 그럼 다시 한번 테스트를 해보겠습니다.  
테스트용 서버에 프로젝트를 배포하고, 해당 프로젝트가 생성한 open file count를 아래 명령어로 확인합니다.

```bash
ls -l /proc/$PID/fd | wc -l
```

PID를 찾고,  

생성된 open file 리스트를 확인할 수 있습니다.  
준비가 다 되었으니, 1100개 요청을 보내는 스크립트를 실행해봅니다!

분명 ```open files```의 값은 1024개로 되어있는데 1330개가 열려있다고 나오다니요.  
  
(한번 실행때마다 1100개의 요청이 간다고 생각하시면 됩니다.)  

2번을 추가로 더 실행해서 **3300개의 요청이 갔음에도 에러없이 처리**되고 있습니다.  
언제 터지는지 확인하기 위해 추가로 더 요청해봅니다 (총 4400개가 갑니다.)  

드디어 Open File 에러가 발생했습니다!  
보시면 4097개까지만 열린채로 에러가 발생한 것을 알 수 있는데요.  

**이전 요청이 3532개를 생성했으니 추가로 1100개를 요청하면 4600개 이상이 생성되어야**하는데 왜 4097개까지만 생성된 것인지 궁금합니다.  
  
혹시나 하는 마음에 ```ulimit -aH```로 soft가 아닌, hard옵션을 확인해봅니다.
  
마침 딱 4096개 입니다!  
프로세스별 open file은 **soft 값이 아닌 hard 값까지 생성**가능한게 아닐까? 라는 추측이 됩니다.  
검증하기 위해 hard의 open files를 **5120**개로 증가시킵니다.  
(soft 옵션은 그대로 1024개 입니다.)  


자 그리고 다시 요청을 진행해보겠습니다.  
이번에 4096개가 넘는 요청이 가능해진다면 soft가 아닌, hard 값까지 생성 가능하다는걸 알수있겠죠?  

예상대로 4000개가 넘는 API 요청 (4635)이 가능해졌습니다!

실제로 ```ulimit -a```로 확인할 수 있는 **soft 값으로 소켓 생성이 제한되지는 않는다**는 것을 알 수 있습니다.  
결국 **소켓 생성 제한은 hard 옵션에 따라간다** 라는 이야기가 됩니다....  

  
soft limit으로 1024, hard limit으로 4096인걸 확인하고, **파이썬 스크립트로 file을 임의로** 열어봅니다.

> 여기서 3개를 빼야하는 이유는 stdin, stdout, stderr의 표준 입/출력이 포함됐기 때문입니다.

1021개에서 1개를 더 추가하니 바로 ```Too many open files``` Error가 발생합니다!  

헉? soft 옵션이 파이썬에서 잘 적용된걸까요?  
  
open files soft 값을 2000으로 증가시킨후 다시 1997개가 넘는 file을 open 해봅니다.


여기서도 마찬가지로 1997개 이상 file open시에 바로 ```Too many open files``` Error가 발생합니다.  
  
**왜 파이썬은 soft옵션까지만 file이 오픈되고, Java에선 hard 옵션까지 file이 오픈되는건지** 이상했습니다.  

이상하단 생각에 ```strace```로 JVM 로그를 확인해보니!

이렇게 ```setrlimit```으로 limit을 업데이트하는 로그가 찍혀있습니다!  
  
왜 이런 로그가 발생했는지 [오라클 Java 옵션](http://www.oracle.com/technetwork/articles/java/vmoptions-jsp-140102.html)을 찾아봤습니다.  
문서에는 ```MaxFDLimit``` 라는 옵션이 있었는데요, 뭔가 file limit과 관련돼 보입니다. 


이 옵션이 뭔지 찾아보니 [openjdk](https://github.com/dmlloyd/openjdk/blob/c3f27ada97987466e9c6e33e02e676bd69b78664/src/hotspot/os/linux/os_linux.cpp#L4998) 코드에서 **이 옵션이 true일 경우 ```setlimit``` 으로 limit을 증가**시키는 것을 확인할 수 있습니다.


그리고 설치된 Java의 ```MaxFDLimit``` **기본값이 true**임을 확인할 수 있습니다.

즉, **리눅스 OS에서 JDK 실행시 자동으로 limit 사이즈를 증가**시켜준다는 것을 알 수 있습니다.  

## 결론

위 2개의 실험으로 얻은 결론입니다.

* Java에서 동시에 생성 가능한 쓰레드 수는 ```max user processes```를 따라간다.
* Java에서 소켓 통신(HTTP API, JDBC 커넥션 등)은  ```open file``` 옵션을 따라간다.
  * 단, JDK 내부 코드상에서 hard limit이 soft limit에 적용된다.


> 참고로 Tomcat은 8 버전부터 기본 Connector 방식을 NIO로 사용합니다.  
(7 버전까지는 BIO)  
그러다보니 maxConnections은 10,000, maxThreads는 200이 기본값입니다.  
(BIO에서는 둘의 값이 동일해야 합니다)  
이번 테스트에서 사용되는 connection 수가 1만을 넘지 않기 때문에 기본옵션으로 진행했습니다.

## 번외) limit 옵션 설정방법

보통 soft limit과 hard limit을 별도로 관리하진 않습니다.  
둘의 값을 동일하게 적용하는데요.  
서버의 open files, max user processes등 옵션을 permanent (영구) 적용하기 위해선 ```/etc/security/limits.conf``` 을 수정하면 됩니다.


## 참고

* [호스트웨이 ulimit 설정관련](http://faq.hostway.co.kr/Linux_ETC/7179)
* [리눅스 서버의 TCP 네트워크 성능을 결정짓는 커널 파라미터 이야기](http://meetup.toast.com/posts/53)


