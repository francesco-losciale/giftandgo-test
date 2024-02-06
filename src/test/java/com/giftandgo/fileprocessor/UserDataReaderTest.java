package com.giftandgo.fileprocessor;

import com.giftandgo.fileprocessor.model.UserData;
import com.giftandgo.fileprocessor.utils.UserDataReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataReaderTest {
    private UserDataReader userDataReader = new UserDataReader();

    @Test
    void shouldCreateUserData() {
        String fileContent = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" +
                "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" +
                "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3";

        assertThat(userDataReader.readFileContent(fileContent)).isEqualTo(List.of(
                UserData.fromString("18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n"),
                UserData.fromString("3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n"),
                UserData.fromString("1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3")
        ));
    }

//    @Test
//    void shouldReturnEmptyListWhenContentIsEmpty() {
//        assertThat(userDataReader.readFileContent("")).isEqualTo(List.of());
//    }
}
