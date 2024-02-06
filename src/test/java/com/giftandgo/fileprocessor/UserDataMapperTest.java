package com.giftandgo.fileprocessor;

import com.giftandgo.fileprocessor.model.Output;
import com.giftandgo.fileprocessor.model.UserData;
import com.giftandgo.fileprocessor.model.UserDataMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataMapperTest {

    @Test
    void shouldMapUserDataToOutput() {
        String content = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n";
        UserData userData = UserData.fromString(content);

        assertThat(UserDataMapper.mapToJsonOutput(userData)).isEqualTo(new Output("John Smith", "Rides A Bike", 12.1));
    }
}
