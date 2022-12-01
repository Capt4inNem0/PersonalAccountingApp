package dev.cnemo.personalaccounting.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.cnemo.personalaccounting.adapters.AccountMoveLineAdapter
import dev.cnemo.personalaccounting.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val movimientosRecyclerView: RecyclerView = binding.frDashboardMovimientos
        movimientosRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AccountMoveLineAdapter(mutableListOf())
        }

        dashboardViewModel.accountMoves.observe(viewLifecycleOwner) {
            (movimientosRecyclerView.adapter as AccountMoveLineAdapter).setData(it)
        }
        dashboardViewModel.firstLoad(requireContext())
        dashboardViewModel.listenAccountMoves(requireContext())

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}