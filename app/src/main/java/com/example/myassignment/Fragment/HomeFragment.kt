package com.example.myassignment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassignment.Adapter.ArticleAdapter
import com.example.myassignment.Model.Article
import com.example.myassignment.R

class HomeFragment : Fragment() {
    private lateinit var adapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var articlesArrayList: ArrayList<Article>

    lateinit var imageId : Array<Int>
    lateinit var title : Array<String>
    lateinit var description : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ArticleAdapter(articlesArrayList)
        recyclerView.adapter = adapter
    }

    companion object {
    }

    private fun dataInitialize(){
        articlesArrayList = arrayListOf<Article>()

        imageId = arrayOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,
        )

        title = arrayOf(
            "TechCrunch",
            "The Verge",
            "Engadget",
            "Gizmodo",
            "A Cup of Jo",
            "Minimalism Life",
            "He Spoke Style",
            "The Zoe Report",
            "Sincerely Jules",
            "Color Me Courtney",
        )

        description = arrayOf(
            "The TechCrunch website’s clean layout prioritizes text readability with its simple white background and black text.",
            "The Verge’s website homepage is vibrant – a black and white theme with bright accents of orange and magenta.",
            "Launched by Peter Rojas, Engadget is a technology blog providing reviews of gadgets and consumer electronics as well as the latest news in the tech world.",
            "Gizmodo is another technology blog created by Peter Rojas. The blog publishes news and opinion pieces on technology, gadgets, science, entertainment, culture, and the environment.",
            "The website sports a warm-toned theme, with plenty of space between the site elements.",
            "Even if you don’t subscribe to the Minimalism Life, you can follow this site’s clutter-free and straightforward approach to design your blog.",
            "The blog title’s serif font gives off a classy and premium feel, appropriate for a website on style inspiration.",
            "Created by an American fashion designer and stylist Rachel Zoe, The Zoe Report’s articles cover fashion trends, style guides, makeup, home decor, and even celebrity fashion and lifestyle.",
         "Founded as a creative outlet by beauty and fashion blogger Julie Sariñana, Sincerely Jules is a blog that provides outfit ideas, beauty tips, and reviews of her favorite products.",
            "Aiming to empower others to be confident and dress outside the lines, Color Me Courtney is a fashion and lifestyle blog belonging to Courtney Quinn, a fashionista obsessed with color.",
            )

        for (i in imageId.indices){
            val article = Article(imageId[i],title[i],description[i])
            articlesArrayList.add(article)
        }

    }
}