
//S1803445
package org.me.gcu.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bon.earthquake.R;
import com.bon.earthquake.databinding.FragmentHomeBinding;
import org.me.gcu.models.Item;
import org.me.gcu.ui.adapters.ItemListAdapter;
import org.me.gcu.ui.main.MainViewmodel;
import org.me.gcu.ui.main.ViewModelFactory;

import org.me.gcu.utils.DateParser;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialDatePicker.Builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() { }
    private FragmentHomeBinding binding;
    private MainViewmodel viewmodel;
    ArrayList<Item> filtered = new ArrayList<>();
    private List<Item> items;
    private List<Item> changingItems;
    private boolean changed = false;
    private final DateParser parser = new DateParser();
    private Date startDate;
    private Date endDate;
    private Date sortDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Connect the fragment to viewmodel
        ViewModelFactory factory = new ViewModelFactory(requireContext());
        viewmodel = ViewModelProviders.of(requireActivity(), factory).get(MainViewmodel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Navigation components
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
        NavController navController = navHostFragment.getNavController();

        // Calendar instance to be reused in date picker and range picker
        Calendar calendar = Calendar.getInstance();

        // listen to [Sort By Date] button click
        binding.sortDate.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener date = (vi, y, m, d) -> {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                sortDate = new DateParser().format(calendar);
                filterByDate();
            };

            new DatePickerDialog(requireActivity(), date, calendar.get(1), calendar.get(2), calendar.get(3)).show();
        });


        // listen to the [Sort by Range] button click
        binding.sortRange.setOnClickListener(v -> {
            Builder<Pair<Long, Long>> builder = Builder.dateRangePicker();
            builder.setSelection(new Pair<>(calendar.getTimeInMillis(), calendar.getTimeInMillis()));
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
            picker.addOnCancelListener(dialog -> {
                Toast.makeText(requireContext(), "Please select range", Toast.LENGTH_SHORT).show();
            });
            picker.addOnPositiveButtonClickListener(time -> {
                startDate = parser.formatMillis(time.first);
                endDate = parser.formatMillis(time.second);
                filterByRange();
            });
            picker.show(requireActivity().getSupportFragmentManager(), picker.toString());
        });

        // Observe the livedata in the viewmodel
        viewmodel.data.observe(getViewLifecycleOwner(), data -> {
            if (!changed){
                items = data;
                changed = true;
            }
            changingItems = data;
            ItemListAdapter adapter = new ItemListAdapter(changingItems, item -> {
                // navigating to display fragment
                Bundle bundle = new Bundle();
                bundle.putParcelable("item", item);
                navController.navigate(R.id.detailsFragment, bundle);
            });
            binding.recyclerview.setHasFixedSize(true);
            binding.recyclerview.setAdapter(adapter);
        });
    }

    private void filterByDate(){
        filtered.clear();
        for (Item item: items){
            int itemDate = parser.parse(item.getPubDate()).getDate();
            if (itemDate == sortDate.getDate()) filtered.add(item);
        }
        viewmodel.setItems(filtered);
    }

    private void filterByRange(){
        filtered.clear();
        for (Item item: items){
            Date date = parser.parse(item.getPubDate());
            if (!(date.before(startDate) || date.after(endDate))) filtered.add(item);
        }
        viewmodel.setItems(filtered);
    }

}