package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVO {

    private UUID id;

    private String name;

    private int amount;

}
