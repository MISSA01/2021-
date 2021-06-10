package pers.may.assist.mapper;

import pers.may.assist.pojo.Credit;

import java.util.List;

public interface CreditMapper {

    public void insertItem(Credit credit);

    public List<Credit> selAllItemByPhoneNumber(String phoneNumber);

    public List<Credit> selAllItemByPhoneNumberOfPage(String phoneNumber,Integer pageNum,Integer pageSize);
}
