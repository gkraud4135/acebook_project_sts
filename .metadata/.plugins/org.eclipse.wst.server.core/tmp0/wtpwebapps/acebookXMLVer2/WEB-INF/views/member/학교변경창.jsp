<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html>
<html>
<head>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="js/myjs.js"></script>
<meta charset="UTF-8">
<title>학교변경창</title>
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

.item{

width: 100%;
    height: 29px;
  font-size:20px;
  background-color:transparent;border:0 solid black;
}

</style>
</head>
<body>
<form action="changeschool" method="post">

<!-- wrapper -->
        <div id="wrapper">

            <!-- content-->
            <div id="content">

                <!-- ID -->
                <div>
                    <h3 class="join_title">
                        <label for="id">초등학교</label>
                    </h3>
                    <span class="box int_id">
                        <input type="text" class="item" id="row" name="row" value="${member.school.row ne null ? member.school.row : "" }"/>
                        
                    </span>
                    <span class="error_next_box"></span>
                </div>
                
                 <div>
                    <h3 class="join_title">
                        <label for="id">중학교</label>
                    </h3>
                    <span class="box int_id">
                        <input type="text"  class="item" id="middle" name="middle" value="${member.school.middle ne null ? member.school.middle : "" }"/>
                        
                    </span>
                    <span class="error_next_box"></span>
                </div>
                
                 <div>
                    <h3 class="join_title">
                        <label for="id">고등학교</label>
                    </h3>
                    <span class="box int_id">
                        <input type="text" class="item" id="high" name="high" value="${member.school.high ne null ? member.school.high : "" }"/>
                        
                    </span>
                    <span class="error_next_box"></span>
                </div>
                
                 <div>
                    <h3 class="join_title">
                        <label for="id">대학교</label>
                    </h3>
                    <span class="box int_id">
                        <input type="text" class="item" id="university" name="university" value="${member.school.university ne null ? member.school.university : "" }"/>
                        
                    </span>
                    <span class="error_next_box"></span>
                </div>


                <!-- JOIN BTN-->
              <div class="btn_area">
                    <input type="submit" value="변경하기" id="btnJoin">                                    
                </div>

                 <div class="btn_area"></div>
                <button type="button"  class="button2" id="btnJoin2" onclick="self.close()">취소</button>
            </div>                

            </div> 
            <!-- content-->

        </div> 
        <!-- wrapper -->
    <script src="main.js"></script>
    </form>
    </body>
</html>