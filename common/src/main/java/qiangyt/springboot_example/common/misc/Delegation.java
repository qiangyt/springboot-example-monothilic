package qiangyt.springboot_example.common.misc;

import qiangyt.springboot_example.common.error.InternalException;

public class Delegation<T> {

    private final T target;

    public Delegation(T target) {
        if (target == null) {
            throw new InternalException("target is required");
        }
        this.target = target;
    }

    public T getTarget() {
        return this.target;
    }

}
