package qiangyt.springboot_example.api.vo;

import java.util.UUID;

import lombok.Data;

@Data
public class Product {

    private UUID id;

    private String name;

    private int amount;

}
