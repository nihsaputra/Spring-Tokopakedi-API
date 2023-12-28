package com.enigma.tokopakedi.model;

import com.enigma.tokopakedi.entity.OrderDetail;
import lombok.*;

import java.util.Date;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponse<T> {

    private String id;
    private String customerId;
    private Date transDate;
    private T orderDetail;

}










/*
{
  "id": "1",
  "customerId": "1",
  "transDate": "2023-12-27T08:11:41.310+00:00",
  "orderDetails": [
    {
      "id": "287d6572-a9e0-4cc2-9b45-0db787659541",
      "orderId": "1",
      "product": {
        "id": "01727d8e-dc76-44e7-a627-b0a5dabddef2",
        "name": "Roti Bakar",
        "price": 15000,
        "stock": 2
      },
      "productPrice": 15000,
      "quantity": 1
    }
  ]
}
*/
