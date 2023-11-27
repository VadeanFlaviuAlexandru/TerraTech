import { useEffect, useState } from "react";
import { fetchProductTableData } from "../../api/product/ProductApi";
import DataTable from "../../components/dataTable/DataTable";
import AddProductIcon from "../../components/icons/AddProductIcon";
import ProductModal from "../../components/modals/productModal/ProductModal";
import {
  productTableSetter,
  resetProductTable,
} from "../../store/ProductsTable/ProductTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsProduct, columnsProductModal } from "../../utils/data/columns";
import { warningToast } from "../../utils/toasts/userToasts";
import "./products.scss";

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
    <div className="products">
      <div className="info">
        <h1>Products</h1>
        <button className="addButton" onClick={() => setOpen(true)}>
          <AddProductIcon />
        </button>
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
          buttonText="Add"
          headerText="Add a new product"
          columns={columnsProductModal}
          setOpen={setOpen}
        />
      )}
    </div>
  );
}
