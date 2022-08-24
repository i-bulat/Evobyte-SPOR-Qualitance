export interface Product {
    id?: number;
    name: string;
    description: string;
    subCategory: number | undefined;
    imageUrl: string;
    price: number;
    quantity: number;
}