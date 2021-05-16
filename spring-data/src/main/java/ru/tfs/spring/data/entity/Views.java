package ru.tfs.spring.data.entity;

public class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullParameterType extends IdName {}

    public interface FullParameter extends FullParameterType {}

    public interface FullInfo extends IdName {}

    public interface FullProduct extends FullParameter, FullInfo {}

    public interface FullOrderItem extends IdName {}

    public interface FullCurrency extends IdName {}

    public interface FullClient extends IdName {}

    public interface FullShipment extends IdName {}

    public interface FullOrder extends FullOrderItem, FullCurrency, FullClient, FullShipment {}

    public interface FullPayment extends FullCurrency {}
}
