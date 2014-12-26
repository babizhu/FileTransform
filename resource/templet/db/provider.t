package #packageName#;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bbz.util.db.DatabaseUtil;


/**
 * 该文件自动生成, 禁止手动修改!
 * 模版
 * @author GEN_ROBOT
 * #date
 */
public enum #className# {

    INSTANCE;

	//private static AtomicLong idMax = null;

	public void add( #DTOclassName# #DTOclassParam#, String userName ) {
        PreparedStatement pst = null;
        String sql = "insert into #tableName# ( #columnName# ) values ( #columnQuestionMark# )";
		Connection con = DatabaseUtil.INSTANCE.getConnection();

		try {
			pst = con.prepareStatement( sql );
			#pstAdd#
			pst.executeUpdate();

		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {

            DatabaseUtil.INSTANCE.close( null, pst, con );
		}
	}

    public List<#DTOclassName#> findBy( String field, String condition ) {

            List<#DTOclassName#> list = new ArrayList<#DTOclassName#>();
    		PreparedStatement pst = null;
            ResultSet rs = null;
            #DTOclassName# #DTOclassParam#;
    		String sql = String.format("SELECT * FROM #tableName# WHERE %s=?", field);
            Connection con = DatabaseUtil.INSTANCE.getConnection();

    		try {
    			pst = con.prepareStatement( sql );
    			pst.setString( 1, condition );
    			rs = pst.executeQuery();

    			while (rs.next()) {
    			    #DTOclassParam# = mapping(rs);
    			    list.add( #DTOclassParam# );
    			}
    		} catch (SQLException e) {
    		    e.printStackTrace();
    		} finally {
    			DatabaseUtil.INSTANCE.close( rs, pst, con );
    		}

    		return list;
    	}

    	private #DTOclassName# mapping(ResultSet rs) throws SQLException {

        	#DTOclassName# #DTOclassParam# = new #DTOclassName#();

        	#rsMapping#
        	return #DTOclassParam#;
        }

    public void addAll( List<#DTOclassName#> list, String userName ){
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder( "INSERT INTO #tableName# (#columnName#) VALUES " );
        for (#DTOclassName# #DTOclassParam# : list) {
            sql.append( "(" );
            sql.append( #addAll# );
            sql.append( ")," );
        }
        if( list.size() > 0 ){
           sql.deleteCharAt( sql.length() - 1 );//去掉最后的逗号
        }
        Connection con = DatabaseUtil.INSTANCE.getConnection();

        try {
    	    pst = con.prepareStatement( sql.toString() );
    		pst.executeUpdate();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	} finally {
            DatabaseUtil.INSTANCE.close( null, pst, con );
    	}
    }

    public void delete( #DTOclassName# #DTOclassParam#, String userName ) {
    	PreparedStatement pst = null;
    	String sql = "delete from #tableName# where #keyCondition#";
    	Connection con = DatabaseUtil.INSTANCE.getConnection();

    	try {
    		pst = con.prepareStatement(sql);
            #pstDelete#
            pst.execute();

     	} catch (SQLException e) {
        	e.printStackTrace();

    	} finally {
    	    DatabaseUtil.INSTANCE.close( null, pst, con );
    	}
    }

    public void update( #DTOclassName# #DTOclassParam#, String userName ) {
        	PreparedStatement pst = null;
        	Connection con = DatabaseUtil.INSTANCE.getConnection();

        	String sql = "update #tableName# set #pstUpdateColumns# where #keyCondition#";

        	try {
        		pst = con.prepareStatement(sql);
                #pstUpdate#
                pst.executeUpdate();

         	} catch (SQLException e) {
            	e.printStackTrace();

        	} finally {
        	    DatabaseUtil.INSTANCE.close( null, pst, con );
        	}
        }

}
