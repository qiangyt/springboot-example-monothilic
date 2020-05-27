package qiangyt.springboot_example.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.cglib.beans.BeanCopier;
// import org.springframework.cglib.core.Converter;

import lombok.Getter;

/**
 *
 */
@Getter
public class BeanCopyer<SOURCE,TARGET> {

    private final BeanCopier copier;

    private final Supplier<TARGET> targetSupplier;

    private Function<Integer, TARGET[]> targetArraySupplier;

    // private final Converter converter;

    public BeanCopyer(  Class<SOURCE> sourceClass,
                        Class<TARGET> targetClass,
                        Supplier<TARGET> targetSupplier,
                        Function<Integer, TARGET[]> targetArraySupplier) {
        this.targetSupplier = targetSupplier;
        this.targetArraySupplier = targetArraySupplier;

        this.copier = BeanCopier.create(sourceClass, targetClass, false);
    }

    public TARGET newTarget() {
        return getTargetSupplier().get();
    }

    public TARGET[] newTargetArray(int length) {
        return getTargetArraySupplier().apply(length);
    }

    public TARGET copy(SOURCE source) {
        if (source == null) {
            return null;
        }
        TARGET r = newTarget();
        getCopier().copy(source, r, null);
        return r;
    }

    public TARGET[] copy(Iterable<SOURCE> sources) {
        if (sources == null) {
            return null;
        }

        List<TARGET> r = new ArrayList<>();
        for (SOURCE src: sources) {
            r.add(copy(src));
        }

        return r.toArray(getTargetArraySupplier().apply(r.size()));
    }

}
