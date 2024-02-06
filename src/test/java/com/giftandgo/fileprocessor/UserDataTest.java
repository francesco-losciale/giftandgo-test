package com.giftandgo.fileprocessor;

import com.giftandgo.fileprocessor.model.UserData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataTest {

    @Test
    void shouldCreateUserDataFromContent() {
        String content = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n";

        UserData userData = UserData.fromString(content);

        assertThat(userData.uuid()).isEqualTo(UUID.fromString("18148426-89e1-11ee-b9d1-0242ac120002"));
        assertThat(userData.id()).isEqualTo("1X1D14");
        assertThat(userData.name()).isEqualTo("John Smith");
        assertThat(userData.likes()).isEqualTo("Likes Apricots");
        assertThat(userData.transport()).isEqualTo("Rides A Bike");
        assertThat(userData.avgSpeed()).isEqualTo(6.2);
        assertThat(userData.topSpeed()).isEqualTo(12.1);
    }
}
