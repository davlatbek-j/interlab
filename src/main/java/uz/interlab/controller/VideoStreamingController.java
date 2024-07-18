package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/video")
public class VideoStreamingController
{

    private final ResourceLoader resourceLoader;

    @GetMapping("/{file-name}")
    public ResponseEntity<Resource> getVideo(
            @RequestHeader HttpHeaders headers,
            @PathVariable("file-name") String fileName) throws IOException
    {
        Resource videoResource = resourceLoader.getResource("classpath:uploads/" + fileName);
        Path videoPath = Paths.get(videoResource.getURI());

        long fileSize = videoResource.contentLength();
        List<HttpRange> ranges = headers.getRange();
        if (ranges.isEmpty())
        {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(videoResource);
        }

        HttpRange range = ranges.get(0);
        long start = range.getRangeStart(fileSize);
        long end = range.getRangeEnd(fileSize);

        byte[] data = new byte[(int) (end - start + 1)];
        try (RandomAccessFile file = new RandomAccessFile(videoPath.toFile(), "r"))
        {
            file.seek(start);
            file.readFully(data);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length))
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                .body(new ByteArrayResource(data));
    }
}
