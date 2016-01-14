package com.infosupport.t2c3.test;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.abs.AbsEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

/**
 * An ArrayList based BasicRepository Mock.
 *
 * @param <X> The class of the model
 */
public abstract class MockRepository<X extends AbsEntity> implements BasicRepository<X> {

    private long highestId = 0;
    private ArrayList<X> items;

    /**
     * Initialize the MockRepository.
     */
    public void init() {
        this.highestId = 0;
        this.items = new ArrayList<>();
    }

    @Override
    public List<X> findAll() {
        final List<X> copyList = new ArrayList<>(items.size());
        items.forEach(item -> copyList.add(clone(item)));
        return copyList;
    }

    @Override
    public X findOne(Long id) {
        return getOne(id);
    }

    @Override
    public X getOne(Long id) {
        for (X item : items) {
            if (item.getId().equals(id)) {
                return clone(item);
            }
        }
        return null;
    }

    @Override
    public void delete(X x) {
        delete(x.getId());
    }

    @Override
    public void delete(Long id) {
        for (X item : items) {
            if (item.getId().equals(id)) {
                items.remove(item);
                return;
            }
        }
    }

    @Override
    public void deleteAll() {
        highestId = 0;
        items.clear();
    }

    @Override
    public <S extends X> S save(S s) {
        if (s.getId() == null) {
            ReflectionTestUtils.setField(s, "id", ++highestId);
        }
        items.add(clone(s));
        return s;
    }

    @SuppressWarnings("unchecked")
    protected static <Z> Z clone(Z source) {
        Z target = BeanUtils.instantiate((Class<Z>) source.getClass());
        BeanUtils.copyProperties(source, target);
        return target;
    }


    /**
     * Initialize a MockRepository.
     *
     * This call the actual init function to initialize the properties and
     *  redirect a number of function to the real methods.
     *
     * @param repository The MockRepository
     */
    public static <X extends AbsEntity> void mockInit(MockRepository<X> repository, Class<X> xClass) {
        //Initialize it
        doCallRealMethod().when(repository).init();
        repository.init();

        when(repository.findAll()).thenCallRealMethod();
        when(repository.getOne(anyLong())).thenCallRealMethod();
        when(repository.findOne(anyLong())).thenCallRealMethod();

        doCallRealMethod().when(repository).delete(any(xClass));
        doCallRealMethod().when(repository).delete(anyLong());
        doCallRealMethod().when(repository).deleteAll();

        when(repository.save(any(xClass))).thenCallRealMethod();
    }

}
