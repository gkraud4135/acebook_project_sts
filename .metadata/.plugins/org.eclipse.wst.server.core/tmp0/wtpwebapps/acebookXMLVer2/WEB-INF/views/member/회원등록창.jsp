<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html>
<html>
<head>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="js/myjs.js"></script>
<meta charset="UTF-8">
<title>회원등록</title>
<style type="text/css">

html {
    height: 100%;
}

body {
    margin: 0;
    height: 100%;
    background: #f5f6f7;
    font-family: Dotum,'돋움',Helvetica,sans-serif;
}
#logo {
    width: 240px;
    height: 44px;
    cursor: pointer;
}

#header {
    padding-top: 62px;
    padding-bottom: 20px;
    text-align: center;
}
#wrapper {
    position: relative;
    height: 100%;
}

#content {
    position: absolute;
    left: 50%;
    transform: translate(-50%);
    width: 460px;
}




/* 입력폼 */


h3 {
    margin: 19px 0 8px;
    font-size: 14px;
    font-weight: 700;
}


.box {
    display: block;
    width: 100%;
    height: 51px;
    border: solid 1px #dadada;
    padding: 10px 14px 10px 14px;
    box-sizing: border-box;
    background: #fff;
    position: relative;
}

.int {
    display: block;
    position: relative;
    width: 100%;
    height: 29px;
    border: none;
    background: #fff;
    font-size: 15px;
}

input {
    font-family: Dotum,'돋움',Helvetica,sans-serif;    
}

.box.int_id {
    padding-right: 110px;
}

.box.int_pass {
    padding-right: 40px;
}

.box.int_pass_check {
    padding-right: 40px;
}


.pswdImg {
    width: 18px;
    height: 20px;
    display: inline-block;
    position: absolute;
    top: 50%;
    right: 16px;
    margin-top: -10px;
    cursor: pointer;
}

#bir_wrap {
    display: table;
    width: 100%;
}

#bir_yy {
    display: table-cell;
    width: 147px;
    
}

#bir_mm {
    display: table-cell;
    width: 147px;
    vertical-align: middle;
}

#bir_dd {
    display: table-cell;
    width: 147px;
}

#bir_mm, #bir_dd {
    padding-left: 10px;
}

select {
    width: 100%;
    height: 29px;
    font-size: 15px;
    background: #fff url(https://static.nid.naver.com/images/join/pc/sel_arr_2x.gif) 100% 50% no-repeat;
    background-size: 20px 8px;
    -webkit-appearance: none;
    display: inline-block;
    text-align: start;
    border: none;
    cursor: default;
    font-family: Dotum,'돋움',Helvetica,sans-serif;
}

/* 버튼 */

.btn_area {
    margin: 30px 0 91px;
}

#btnJoin {
    width: 100%;
    padding: 21px 0 17px;
    border: 0;
    cursor: pointer;
    color: white;
    background-color: skyblue;
    font-size: 20px;
    font-weight: 400;
    font-family: Dotum,'돋움',Helvetica,sans-serif;
}
#btnJoin:hover{
color:black;
background-color: DodgerBlue;
}
.button{

background-color: gray; 
 background-color: white;
  color: black;
  border: 2px solid #e7e7e7;
  font-size:18px;
  cursor: pointer;

}
.button:hover {
 background-color: #e7e7e7;
}
.button:active {
  background-color: lightblue;
  box-shadow: 0 1px #666;
  transform: translateY(1px);
}

.button2{

background-color: LightGray;
  border: none;
  color: black;
  padding: 10px 60px;
  text-align: center;
  text-decoration: none;
  display: flex;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;

}
 
</style>
</head>
<body>
<form onsubmit="return 회원등록하다()" action="member" method="post"><!--onsubmit>>>return 값이 true 일때실행  -->


<!-- wrapper -->
        <div id="wrapper">

            <!-- content-->
            <div id="content">

                <!-- ID -->
                <div>
                    <h3 class="join_title">
                        <label for="id">아이디&nbsp;&nbsp;<input class="button" type="button" onclick="아이디중복검사창을띄우다()" value="중복검사"></label>
                    </h3>
                    <span class="box int_id">
                        <input type="text" name ="id" id="id" class="int" maxlength="20" readonly>
                        
                    </span>
                    <span class="error_next_box"></span>
                </div>

                <!-- PW1 -->
                <div>
                    <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>
                    <span class="box int_pass">
                        <input type="password" name="password" id="password1" class="int" maxlength="20">
                        <span id="alertTxt"></span>
                       
                    </span>
                    <span class="error_next_box"></span>
                </div>

                <!-- PW2 -->
                <div>
                    <h3 class="join_title"><label for="pswd2">비밀번호 재확인</label></h3>
                    <span class="box int_pass_check">
                        <input type="password" id="password2" class="int" maxlength="20">
                       
                    </span>
                    <span class="error_next_box"></span>
                </div>

                <!-- NAME -->
                <div>
                    <h3 class="join_title"><label for="name">이름</label></h3>
                    <span class="box int_name">
                        <input type="text" id="name" name="name" class="int" maxlength="20">
                    </span>
                    <span class="error_next_box"></span>
                </div>

                <!-- BIRTH -->
                <div>
                    <h3 class="join_title"><label for="yy">생년월일</label></h3>

                    <div id="bir_wrap">
                        <!-- BIRTH_YY -->
                        <div id="bir_yy">
                            <span class="box">
                                <input type="text" name="year" id="year" class="int" maxlength="4" placeholder="년(4자)">
                            </span>
                        </div>

                        <!-- BIRTH_MM -->
                        <div id="bir_mm">
                            <span class="box">
                                <select id="month" name="month" class="sel">
                                    <option>월</option>
                                    <% for (int i=1; i<=9; i++){ %>
                                        <option value="<%= "0"+i %>"><%="0"+ i %></option>
                                        <%} %>
                                       <% for (int i=10; i<=12; i++){ %>
                                        <option value="<%=i %>"><%=i %></option>
                                        <%} %>
                                </select>
                            </span>
                        </div>

                        <!-- BIRTH_DD -->
                        <div id="bir_dd">
                            <span class="box">
                                <select id="day" name="day">
                                    <option value="">일</option>
                                    <%for(int i=1; i<=9; i++){ %>
                                    <option value="<%="0"+i%>"><%="0"+i%></option>
                                    <%} %>
                                    <%for(int i=10; i<=31; i++){ %>
                                    <option value="<%=i%>"><%=i%></option>
                                    <%} %>
                                </select>                            
                            </span>
                        </div>
                    </div>                             
                </div>                      
<input type="hidden" name="age" id="age"/>
                 <!-- GENDER -->
                 <div>
                    <h3 class="join_title"><label for="gender">성별</label></h3>
                    <span class="box gender_code">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    남성<input type="radio" name="gender" value="남자" id="man" checked>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;여성<input type="radio" name="gender" value="여자" id="woman">         
                    </span>
                </div>

                <!-- JOIN BTN-->
              <div class="btn_area">
                    <input type="submit" value="가입하기" id="btnJoin">                                    
                </div>

                <!-- <div class="btn_area"></div>
                <button type="button"  class="button2" id="btnJoin2" onclick="self.close()">취소</button>
            </div> -->
                

            </div> 
            <!-- content-->

        </div> 
        <!-- wrapper -->
    <script src="main.js"></script>
    </body>
    <input id="close" type="hidden" value="${message}"></input>
</html>
<!-- <input type="hidden" name="age" id="age"/><br><br>
성별&nbsp;&nbsp;&nbsp;남성<input type="radio" name="gender" value="남자" id="man" checked>여성<input type="radio" name="gender" value="여자" id="woman"><br><br>
<input type="submit" value="회원가입"/> <button type="button" onclick="location.href='main'">취소</button> -->

<script>

function 아이디중복검사창을띄우다(){
   window.open("/id"," ","width=450,height=300")
}
function 아이디를받다(id){
   let txtID=document.querySelector("#id");
   txtID.value=id;
}

function 회원등록하다(){
   //필수 입력확인
   var txt성명=document.querySelector("#name");
   if(txt성명.value==""){
      alert("성명은 필수 입력입니다")
      return false;
   
   }
   
   var txtID=document.querySelector("#id");
   if(txtID.value==""){
      alert("아이디는 필수 입력입니다")
      return false;// =서브밋중단
   
   }

   
   var txt패스워드=document.querySelector("#password1");
   if(txt패스워드.value==""){
      alert("패스워드는 필수 입력입니다.")      
      return false;
   }
   

   //비밀번호 동일 확인
   var txt패스워드2=document.querySelector("#password2");
   if(txt패스워드.value !=txt패스워드2.value){
      alert("패스워드가 동일하지 않습니다")
      return false;   
   }
   
   var txtyear=document.querySelector("#year")
   if(txtyear.value==""){
      alert("출생연도를 입력해주세요");
      return false;
   }
   var txtmonth=document.querySelector("#month")
   if(txtmonth.value==""){
      alert("월을 선택하세요");
      return false;
   }
   var txtday=document.querySelector("#day")
   if(txtday.value==""){
      alert("일을 선택하세요");
      return false;
   }
   var txtall=document.querySelector("#age")
   txtall.value=txtyear.value+'-'+txtmonth.value+'-'+txtday.value;
      
    var check=/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/; //최소8자 문자 숫자 특수문자 포함
    var check2=/[0-9]/; //숫자만
    var check3=/[ ~!@#$%^&*()_+|<>?:{}]/; //특수문자



   if(!check.test(txt패스워드.value)){
      alert("비밀번호를 영문,숫자,특수문자의 조합으로 입력해주세요.")
      return false;
   }
    
    if(!check2.test(txtyear.value)){
      alert("출생연도는 숫자만 입력 가능합니다")
      return false;
   }
    
    if(check3.test(txt성명.value)){
      alert("올바른 이름 형식이 아닙니다.")
      return false;
   }
   
   alert("가입이 완료되었습니다");
   return true;/*위의값들이 모두 만족하였을때 트루값 반환  */
}

window.onload = function (){ 
	   
	let 종료 = document.querySelector("#close");
	
      if(종료.value=="close"){
		
		parent.close_layer();
		parent.move();
		
      }

}

</script>