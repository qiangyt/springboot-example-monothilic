package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Data;
import qiangyt.springboot_example.api.enums.AccountRole;

@Data
public class Account {

    private UUID id;

    private String name;

    private String firstName;

    private String secondName;

    private String address;

    private AccountRole[] roles;

}
