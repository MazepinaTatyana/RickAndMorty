package com.example.rickandmorty.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.CharactersAdapter
import com.example.rickandmorty.api.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment(), CharactersAdapter.OnClickCharacterListener {
    private var param1: String? = null

    private val mainViewModel: MainViewModel by viewModels()
    var adapter = CharactersAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewCharacters.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewCharacters.adapter = adapter
        if (isOnline(requireContext())) {
            initAdapter()
            initState()
        } else {
            mainViewModel.result.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
                progressBarLoading.visibility = View.INVISIBLE
            })
        }

    }

    private fun initAdapter() {
        mainViewModel.retry()
        mainViewModel.charactersList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initState() {
        mainViewModel.getState().observe(viewLifecycleOwner, object : Observer<State> {
            override fun onChanged(t: State?) {
                progressBarLoading.visibility =
                    if (mainViewModel.listIsEmpty() && t == State.LOADING) View.VISIBLE else View.GONE
                if (!mainViewModel.listIsEmpty()) {
                    adapter.setState(t ?: State.DONE)
                }
            }
        })
    }

    fun isOnline(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClickCharacter(id: Int) {
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                id
            )
        findNavController().navigate(action)
    }
}
