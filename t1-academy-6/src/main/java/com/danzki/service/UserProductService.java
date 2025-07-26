package com.danzki.service;

import com.danzki.model.ProductType;
import com.danzki.model.User;
import com.danzki.model.UserProduct;
import com.danzki.repository.UserProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProductService {
    private final UserProductRepo productRepo;

    public UserProduct add(String accountNum, Long balance, ProductType productType, User owner) {
        UserProduct userProduct = new UserProduct();
        userProduct.setAccountNum(accountNum);
        userProduct.setBalance(balance);
        userProduct.setProductType(productType);
        userProduct.setUser(owner);

        return productRepo.save(userProduct);
    }

    public List<UserProduct> findAllByUser(User user) {
        return productRepo.findAllByUser(user.getId());
    }

    public UserProduct findById(Long id) {
        Optional<UserProduct> userProductOpt = productRepo.findById(id);
        return userProductOpt.orElse(null);
    }

    public List<UserProduct> findAll() {
        return productRepo.findAll();
    }

    public void delete(UserProduct product) {
        productRepo.delete(product);
    }

}
