package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import common.JDBCUtil;
import format.Board;
import format.User;

public class BoardDao {
	public BoardDao() {
		// TODO Auto-generated constructor stub
	}
	
	// 게시글 수 구하기
	public int getCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(id) FROM board";

		conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int count = rs.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	// 게시글 추가
	public boolean addBoard(String title, String contents, User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO board(title, contents, user_id, upload_at, id) values(?,?,?,?, NVL((SELECT MAX(id) FROM board),0)+1 )";

		conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setString(3, user.getId());
			pstmt.setDate(4, Date.valueOf(LocalDate.now()));
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// 게시글 리스트 형식으로 받기
	public ArrayList<Board> getBoardList(int start, int count) {
		ArrayList<Board> boardList = new ArrayList<Board>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board WHERE id BETWEEN ? AND ? ORDER BY id DESC";
		
		int maxCount = this.getCount(); 
		
		conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, maxCount-(count + start - 1));
			pstmt.setInt(2, maxCount-start);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String userId = rs.getString("user_id");
				String title = rs.getString("title");
				String contents = rs.getString("contents");
				Date uploadAt = rs.getDate("upload_at");

				boardList.add(new Board(id, userId, title, contents, uploadAt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return boardList;
	}
}
