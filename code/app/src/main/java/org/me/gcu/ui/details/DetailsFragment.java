//S1803445
//Andrew Minja
package org.me.gcu.ui.details;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bon.earthquake.R;
import com.bon.earthquake.databinding.FragmentDetailsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.models.Item;
import org.me.gcu.utils.ColorCode;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private Item item;
    private int markerColor;

    // initialise the map
    private final OnMapReadyCallback callback = googleMap -> {
        LatLng location = new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()));
        MarkerOptions options = new MarkerOptions()
                .icon(getMarker(markerColor))
                .position(location)
                .title(item.getLocation());
        googleMap.addMarker(options);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        item = getArguments().getParcelable("item");

        // Get Marker color
        ColorCode colorCode = new ColorCode();
        colorCode.setColor(item.getMagnitude());
        markerColor = colorCode.getColor();

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        binding.location.setText("Location: "+ item.getLocation());
        binding.magnitude.setText("Magnitude: "+ item.getMagnitude());
        binding.depth.setText("Depth: "+item.getDepth());
        binding.time.setText("Time: "+ item.getPubDate());
        binding.btnReadMore.setOnClickListener(v -> {
            Uri uri = Uri.parse(item.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    // method definition
    private BitmapDescriptor getMarker(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
