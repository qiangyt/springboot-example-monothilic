package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountVO {

    private UUID id;

    private String firstName;

    private String secondName;

    private String address;

}
