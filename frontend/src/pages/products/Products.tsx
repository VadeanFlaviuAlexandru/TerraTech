import "./products.scss";
import DataTable from "../../components/dataTable/DataTable";
import { useEffect, useState } from "react";
import { fetchProductTableData } from "../../api/product/ProductApi";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import {
  productTableSetter,
  resetProductTable,
} from "../../store/ProductsTable/ProductTableSlice";
import { warningToast } from "../../utils/toasts/userToasts";
import { columnsProduct, columnsProductModal } from "../../utils/data/columns";
import ProductModal from "../../components/modals/productModal/ProductModal";

export default function Products() {
  const [open, setOpen] = useState(false);
  const currentUser = useAppSelector((state) => state.currentUser);
  const currentProducts = useAppSelector((state) => state.productsTable);
  const dispatch = useAppDispatch();

  useEffect(() => {
    const fetchData = async () => {
      try {
        await fetchProductTableData(
          currentUser.user.id,
          currentUser.token
        ).then((response) => {
          dispatch(productTableSetter(response));
        });
      } catch (err: any) {
        warningToast(err.stringify);
      }
    };
    fetchData();
    return () => {
      resetProductTable();
    };
  }, [currentUser.user.id]);

  return (
    <div className="users">
      <div className="info">
        <h1>Products</h1>
        <button onClick={() => setOpen(true)}>Add new products</button>
      </div>
      <DataTable
        userTable={false}
        slug="products"
        columns={columnsProduct}
        rows={currentProducts.products}
      />
      {open && (
        <ProductModal
          product={null}
          editableMode={false}
          id={-1}
          buttonText="add"
          headerText="product"
          columns={columnsProductModal}
          setOpen={setOpen}
        />
      )}
    </div>
  );
}
