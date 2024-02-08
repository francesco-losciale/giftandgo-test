package com.giftandgo.fileprocessor.service;

import com.giftandgo.fileprocessor.model.Output;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileProcessorServiceTest {

    @Test
    void shouldProduceOutput() {
        FileProcessorService service = new FileProcessorService();
        String content = """
                18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
                3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
                1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3""";

        List<Output> result = service.generateOutput(content);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).name()).isEqualTo("John Smith");
        assertThat(result.get(1).name()).isEqualTo("Mike Smith");
        assertThat(result.get(2).name()).isEqualTo("Jenny Walters");

    }
}
