package fourteam.fantastic.btl;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fourteam.fantastic.btl.api.ProductApi;
import fourteam.fantastic.btl.api.SearchImageApi;
import fourteam.fantastic.btl.model.Category;
import fourteam.fantastic.btl.model.CategoryAdapter;
import fourteam.fantastic.btl.model.Product;
import fourteam.fantastic.btl.model.ProductAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class HomeListFragment extends Fragment {
    private RecyclerView recyclerViewProduct;
    private RecyclerView recyclerViewCategory;
    private ProductAdapter adapterProduct;
    private CategoryAdapter adapterCategory;
    final int GALLERY_REQ_CODE = 1000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
//        Gson gson = new Gson();
//        String strJson =  gson.toJson(getLists());
        List<Product> listProduct = new ArrayList<>();
        System.out.println("Call Api Product List");
        getProductListApi(view, listProduct);

        // Set the GridLayout's columns and rows.

        List<Category> listCategory = new ArrayList<>();
        System.out.println("Call Api Category List");
        getCategoryListApi(view, listCategory);

//        get Intent nhận
//        Intent intent = getActivity().getIntent();
//        String token = intent.getStringExtra("token");
//        System.out.println("token: " + token);

//        search
        SearchView searchView = view.findViewById(R.id.search_view);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit (String query){
                    // Perform the search.
                    // TODO: Implement the search logic here.
                    System.out.println("query_from_home: " + query);
                    Intent intent = getActivity().getIntent();
                    String token = intent.getStringExtra("token");
                    System.out.println("token: " + token);
                    String query_from_home = query;
                    Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
                    intentSearch.putExtra("query_from_home", query);
                    intentSearch.putExtra("token", token);
                    startActivity(intentSearch);
                    return false;
                }

                @Override
                public boolean onQueryTextChange (String newText){
                    // Do nothing.
                    System.out.println(newText);
                    return false;
                }
            });
        }

        ImageButton searchImageButton = view.findViewById(R.id.searchImageButton);
        if(searchImageButton != null){
            System.out.println("checkkkkkkkkkkk");
            searchImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
                    System.out.println("check image buitton");

                }
            });
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
                String string  = dateFormat.format(new Date());
                System.out.println(string);
                MultipartBody.Part file = MultipartBody.Part.createFormData("file", string + ".jpg", requestBody);
                System.out.println("Checkkkkkkk imgae uploaded");
                String[] searchImageString = {""};
                searchImageApi(getView(), file, searchImageString);
                System.out.println("chefsdlaskld" + searchImageString[0]);
            } catch (IOException e) {
                System.out.println("Error getting image from gallery");
            }
        }
    }

    private List<Product> getLists(){
        List<Product> list = new ArrayList<>();
        list.add(new Product(1,"", "Product 1", "100" + ".000 đ"));
        list.add(new Product(2,"", "Product 2", "200" + ".000 đ"));
        list.add(new Product(3,"", "Product 3", "400" + ".000 đ"));
        list.add(new Product(4,"", "Product 4", "300" + ".000 đ"));
        list.add(new Product(5,"", "Product 5", "100" + ".000 đ"));
        list.add(new Product(6,"", "Product 6", "600" + ".000 đ"));
        list.add(new Product(7,"", "Product 7", "300" + ".000 đ"));
        return list;
    }

    private void getProductListApi(View view, List<Product> list){
        ProductApi.retrofit.getAllProducts(null, null, null, null).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                Gson gson = new Gson();
                list.clear();
                String productList = gson.toJson(response.body());
                JsonElement root = new JsonParser().parse(productList);
                int size = root.getAsJsonObject().get("data").getAsJsonArray().size();
                System.out.println("size" + size);
                for (int i=0;i<size;i++){
                    JsonObject value1 = root.getAsJsonObject().get("data").getAsJsonArray().get(i).getAsJsonObject();
                    Integer id = (int) Double.parseDouble(value1.get("id").getAsString());
                    String image = value1.get("images").getAsJsonArray().get(0).getAsJsonObject().get("image").getAsString();
                    String replaceString = getResources().getString(R.string.ip_config) + ":" + getResources().getString(R.string.port_product);
                    image = image.replace("product-service:9000", replaceString);
//                    Log.e("Product List " + id + ": ", image);
                    String title = value1.get("title").getAsString();
                    String price = value1.get("price").toString() + ".000 đ";
                    list.add(new Product(id, image, title, price));

                }
                System.out.println("a "+ list.toString());

                // Find recycle view
                recyclerViewProduct = view.findViewById(R.id.rProductLists);

                // Create a new Adapter class that extends RecyclerView.Adapter.
                adapterProduct = new ProductAdapter(list);

                // Set the Adapter class on the RecyclerView.
                recyclerViewProduct.setAdapter(adapterProduct);

                // Set the RecyclerView's layout manager.
                GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
                recyclerViewProduct.setLayoutManager(layoutManager);

//                System.out.println("b "+ lists.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }

    private void getCategoryListApi(View view, List<Category> list){
        ProductApi.retrofitCategory.getAllCategories().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                I/System.out
                Gson gson = new Gson();
                list.clear();
                String productList = gson.toJson(response.body());
                JsonElement root = new JsonParser().parse(productList);
                int size = root.getAsJsonObject().get("results").getAsJsonArray().size();
                for (int i=0;i<size;i++){
                    JsonObject value1 = root.getAsJsonObject().get("results").getAsJsonArray().get(i).getAsJsonObject();
                    Integer id = (int) Double.parseDouble(value1.get("id").getAsString());
                    String title = value1.get("name").getAsString();
                    list.add(new Category(id,title));

                }
                System.out.println("category "+ list.toString());

                // Find recycle view
                recyclerViewCategory = view.findViewById(R.id.rCategories);

                // Create a new Adapter class that extends RecyclerView.Adapter.
                adapterCategory = new CategoryAdapter(list);

                // Set the Adapter class on the RecyclerView.
                recyclerViewCategory.setAdapter(adapterCategory);

                // Set the RecyclerView's layout manager.
                LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewCategory.setLayoutManager(layoutManager);

//                System.out.println("b "+ lists.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("err: " +t);
            }
        });
    }

    private void searchImageApi(View view, MultipartBody.Part requestBody, String[] resString) {
        SearchImageApi.retrofit.searchImage(requestBody).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    resString[0] = response.body().toString();
                    System.out.println("uweiuqyuweiyq" + resString[0]);
                    Intent intent = getActivity().getIntent();
                    String token = intent.getStringExtra("token");
                    Intent intent2 = new Intent(view.getContext(), SearchActivity.class);
                    intent2.putExtra("token", token);
                    intent2.putExtra("query_from_home", resString[0]);
                    startActivity(intent2);
                } else {
                    System.out.println("failed");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}