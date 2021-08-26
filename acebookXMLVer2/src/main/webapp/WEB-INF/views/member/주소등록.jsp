<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.lec206.acebook.util.BIRTH" %>
<!DOCTYPE html>
<html>
<head>
<style>
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
<meta charset="UTF-8">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<title>Insert title here</title>
</head>
<body>

<form action="addpost" method="post">

<div id="wrapper">
            <!-- content-->
            <div id="content">

                <!-- ID -->
                <div>
                    <h3 class="join_title">
                        <label for="id">우편번호&nbsp;&nbsp;</label><input type="button" onclick="sample4_execDaumPostcode()" value="조회"/>
                    </h3>
                    <span class="box int_id">
                        <input type="text" name="post" id="post" class="int" value="${member.post.post ne null ? member.post.post : " "}"readonly/>
                        
                    </span>
                    <span class="error_next_box"></span>
                </div>


                <!-- PW2 -->
                <div>
                    <h3 class="join_title"><label for="pswd2">주소</label></h3>
                    <span class="box int_pass_check">
                       <input type="text" name="address" id="address" class="int" value="${member.post.address ne null ? member.post.address : " "}"readonly/><br>
                       
                    </span>
                    <span class="error_next_box"></span>
                </div>

                <!-- NAME -->
                <div>
                    <h3 class="join_title"><label for="name">상세주소</label></h3>
                    <span class="box int_name">
                        <input type="text" name="addresstail" id="addresstail" class="int" value="${member.post.addresstail ne null ? member.post.addresstail : " "}"/><br>
                    </span>
                    <span class="error_next_box"></span>
                </div>

                <!-- BIRTH -->
                <div>
                    <h3 class="join_title"><label for="yy">출신지</label></h3>

                    <div id="bir_wrap">
                        <!-- BIRTH_YY -->
                        <div id="bir_yy">
                            <span class="box">
                            <select id="birthpalce" name="birthplace">
								<% for(BIRTH type : BIRTH.values()) { %>
								<option value="<%=type%>"><%=type %></option>
									<%} %>
									</select>
                            </span>
                        </div>
                        </div>


                <!-- JOIN BTN-->
              <div class="btn_area">
                    <input type="submit" value="등록하기" id="btnJoin">                                    
                </div>
                
                <div class="btn_area"></div>
                

            </div> 
            <!-- content-->

<input type="button" onclick="location.href='/main'" value="건너뛰기"/>
        </div> 



<%-- <!-- 7-14 JSTL 로 null처리 완료 -->
<form action="changepost" method="post">
우편번호<input type="text" name="post" id="post" value="${member.post.post ne null ? member.post.post : " "}"readonly/><input type="button" onclick="sample4_execDaumPostcode()" value="조회"/><br>
주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소<input type="text" name="address" id="address" value="${member.post.address ne null ? member.post.address : " "}"readonly/><br> 
상세주소<input type="text" name="addresstail" id="addresstail" value="${member.post.addresstail ne null ? member.post.addresstail : " "}"/><br>
출신지
<select id="birthpalce" name="birthplace">
<% for(BIRTH type : BIRTH.values()) { %>
<option value="<%=type%>"><%=type %></option>
<%} %>
</select>
<input type="submit" value="변경하기"/> 
<button type="button" onclick=self.close()>취소</button> --%>
</form>
</body>
</html>

<!-- javaScript -->
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('post').value = data.zonecode;
                document.getElementById("address").value = roadAddr;
                document.getElementById("addresstail").focus();               
               
            }
        }).open();
    }
</script>