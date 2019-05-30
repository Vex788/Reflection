package com.company;

import java.io.FileWriter;
import java.io.IOException;

@Test2SaveTo(path = "d:\\some_text.txt")
public class Task2TextContainer {
    String someText = "Some text 12345\r\n";

    @Test2Saver
    public boolean saveToFile(String path) throws IOException {
        boolean operationResult = false;

        if (someText != null) {
            FileWriter fileWriter = new FileWriter(path);
            try {
                fileWriter.write(someText);
                fileWriter.close();

                operationResult = true;
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return operationResult;
    }
}
