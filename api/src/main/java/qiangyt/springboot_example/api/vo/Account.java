package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Data;

@Data
public class Account {

    private UUID id;

    private String name;

    private String firstName;

    private String secondName;

    private String address;

    private String roles; // comma separated role names

}
