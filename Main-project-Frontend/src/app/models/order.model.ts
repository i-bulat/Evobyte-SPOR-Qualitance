import { LineItem } from "./line-item.model";
import { User } from "./user.model";

export interface Order {
    id?: number;
    dateCreated?: string;
    status: string;
    lineItems: LineItem[];
    totalPrice: number;
}

export interface OrderPost {
    id?: number;
    dateCreated?: string;
    status: string;
    user?: User;
}