package ru.galuzin.connection;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ru.galuzin.base.Segment;
import ru.galuzin.executor.PreparedExecutor;
import ru.galuzin.executor.TExecutor;

public class DBServiceUpdate extends DBServiceConnection {
    private static final String CREATE_TABLE_SEGMENT = "create table if not exists %s (" +
            "timestmp TIMESTAMP" +
            ", text varchar(256)" +
            ", id INT auto_increment primary key"+
            ");";
            //", primary key (id))";
    private static final String INSERT_DATA = "insert into %s (timestmp, text) values (?, ?)";
    private static final String DELETE_USER = "drop table user";
    private static final String VIEW_NAME = "TEST_VIEW";
    private static final String VIEW = "CREATE OR REPLACE VIEW "+VIEW_NAME+" AS ";
    private static final String TEXT_COLUMN_NAME = "text";
    private static final String VIEW_ITER = "SELECT "+TEXT_COLUMN_NAME+",id"+" FROM %s";
    private static final String UNION = " UNION ";
    private static final String CLOSE = ";";
    private static final Random rand = new Random();
    final List<Segment> segments = new ArrayList<Segment>();

    public void createSegment(String name) throws SQLException {
        PreparedExecutor exec = new PreparedExecutor(getConnection());
        exec.execUpdate(String.format(CREATE_TABLE_SEGMENT,name), statement -> statement.execute());
        System.out.println("Table created");
        segments.add(new Segment(name));
    }

    public void generateSegmentData() throws SQLException{
        Segment currentSegment = segments.get(segments.size() - 1);
        PreparedExecutor exec = new PreparedExecutor(getConnection());
        exec.execUpdate(String.format(INSERT_DATA,currentSegment.getName()), statement ->{
            statement.setDate(1,Date.valueOf(LocalDate.now()));
            statement.setString(2,getRandomStr());
            statement.execute();
        } );
    }

    public void createView() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(VIEW);
        Iterator<Segment> iterator = segments.iterator();
        while (iterator.hasNext()) {
            Segment next = iterator.next();
            sb.append(String.format(VIEW_ITER,next.getName()));
            if(iterator.hasNext()){
                sb.append(UNION);
            }else {
                sb.append(CLOSE);
            }
        }
        PreparedExecutor exec = new PreparedExecutor(getConnection());
        exec.execUpdate(sb.toString(), statement -> statement.execute());
        System.out.println("View created "+sb.toString());
    }

    public List<String> selectFromView() throws SQLException {
        TExecutor executor = new TExecutor(getConnection());
        return executor.execQuery("select "+TEXT_COLUMN_NAME+",id"+" from "
                +VIEW_NAME + " order by id", result -> {
            List<String> texts = new ArrayList<>();
            while (!result.isLast()) {
                result.next();
                texts.add(result.getInt("id")+";"+result.getString(TEXT_COLUMN_NAME));
            }
            return texts;
        });
    }

    public void deleteTables() throws SQLException {
        PreparedExecutor exec = new PreparedExecutor(getConnection());
        exec.execUpdate(DELETE_USER,statement -> statement.execute());
        System.out.println("Table dropped");
    }

    static String getRandomStr(){
        return String.format("%04d", rand.nextInt(10000));
    }
}
