package setup;

//@Mapper
public interface MapperSetup {
	
	/*//foreach
	@Insert({"<script>", 
		"insert into attach(name,size,board_no) values ",
		"<foreach collection='attachs' item='attach' index='index' separator=','>", 
		"(#{attach.name},#{attach.size},#{attach.board.no})",	
		"</foreach>",
		"</script>"
		})
	void saves(@Param(value="attachs")List<Attach> 첨부파일들);
*/
	/* 
@Select("select * from board where no=#{no} order by wdate desc limit 0,#{size}")
@Results(value = {
		@Result(property = "no", column = "no"),
		@Result(property = "contents", column = "contents"),
		@Result(property = "wDate", column = "wdate"),
		@Result(property = "writer.no", column = "writer"),
		@Result(property = "board.no", column = "board_no")})
   List<Comment> selectByBoardNo(int 게시물번호,int size);
*/
	/*
@Select("select * from board where no=#{no}")
	@Results(value = {
		@Result(property = "no", column = "no"),
		@Result(property = "title", column = "title"),
		@Result(property = "contents", column = "contents"),
		@Result(property = "wDate", column = "wdate"),
		@Result(property = "views", column = "views"),
		@Result(property = "writer", column = "writer", one=@One(select =  "com.stone.mvc.dataservice_member."+"IMemberMapper.findByNo")),
		@Result(property = "attachs", javaType = List.class, column = "no",many=@Many(select = "com.stone.mvc.dataservice_board."+"IAttachMapper.load")) })
		 
	Board findByNo(int no);
	*/

}
