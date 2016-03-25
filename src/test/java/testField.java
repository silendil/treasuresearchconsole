import com.treasuresearch.labirint.Maze;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class testField {
    @Test
    public void testFieldLoad() throws IOException {
        testPartFieldLoad("d:\\temp\\maze\\2.json","d:\\temp\\maze\\1.txt");
        testPartFieldLoad("d:\\temp\\maze\\1.json","d:\\temp\\maze\\1.txt");
    }

    public void testPartFieldLoad(String fileJson, String fileText) throws IOException {
        FileInputStream fis = new FileInputStream(fileJson);
        Maze maze = new Maze();
        maze.loadField(fis);
        fis = new FileInputStream(fileText);
        Scanner scanner = new Scanner(fis);
        int row = 0;
        while (scanner.hasNext()){
            StringBuilder line = new StringBuilder("");
            for(int i = 0; i < maze.getHorizontalSize();i++ )
                line.append(maze.getContent(i,row));
            Assert.assertEquals(line.toString(),scanner.nextLine());
            row++;
        }
    }
}
