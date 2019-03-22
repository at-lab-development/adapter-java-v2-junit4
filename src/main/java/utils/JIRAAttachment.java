package utils;

import constant.FileConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JIRAAttachment {
    private static List<File> attachmentList = new ArrayList<>();

    private JIRAAttachment() {
    }

    public static void addAttachment(File file, String JIRATestKey){
        try {
            File attachment = copyFileData(file, JIRATestKey);
            attachmentList.add(attachment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File copyFileData(File file, String JIRATestKey) throws IOException {
        File attachment = null;
        String fileName = file.getName();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            attachment = new File(FileConstants.ATTACHMENT_PATH + addJIRATestKeyInFileName(fileName, JIRATestKey, FilenameUtils.getExtension(file.getName())));
            byte[] data = IOUtils.toByteArray(fileInputStream);
            FileUtils.writeByteArrayToFile(attachment, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return attachment;
    }

    private static String addJIRATestKeyInFileName(String fileName, String JIRATestKey, String fileExtension) {
        return FilenameUtils.getBaseName(fileName) + FileConstants.JIRA_KEY_PREFIX + JIRATestKey + FileConstants.FILE_EXTENSION_SEPARATOR + fileExtension;
    }

    public static List<File> getAttachmentList() {
        return attachmentList;
    }
}
