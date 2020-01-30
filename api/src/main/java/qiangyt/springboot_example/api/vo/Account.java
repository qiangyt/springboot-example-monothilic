package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Data;

@Data
public class Account {

    private UUID id;

    private String firstName;

    private String secondName;

    private String address;

}
