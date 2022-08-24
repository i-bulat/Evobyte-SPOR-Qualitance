import { Order } from "./order.model";
import { Product } from "./product.model";

export interface LineItemPost {
    id?: number;
    quantity?: number;
    order_id?: number;
    product_id?: number;
}

export interface LineItem {
    id?: number;
    quantity?: number;
    order?: Order;
    product?: Product;
}
