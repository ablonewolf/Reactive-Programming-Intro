package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.impl.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonBlockingFileHandlingDemo {

    private static final Logger log = LoggerFactory.getLogger(NonBlockingFileHandlingDemo.class);

    public static void main(String[] args) {
        var fileService = new FileServiceImpl();

        fileService.read("sample_file.txt")
            .subscribe((value) -> {
                log.info("File Contents: {}", value);
                fileService.write("new_file.txt", value)
                    .subscribe(Util.subscriber("File Writer subscriber"));
                fileService.write("File_to_be_deleted.txt", value)
                    .subscribe();
            });

        fileService.delete("File_to_be_deleted.txt")
            .subscribe(Util.subscriber("File Delete subscriber"));

    }
}
