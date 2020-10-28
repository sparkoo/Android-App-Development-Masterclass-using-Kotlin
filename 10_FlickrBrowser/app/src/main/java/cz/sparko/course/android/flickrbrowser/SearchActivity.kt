package cz.sparko.course.android.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView

private const val TAG = "SearchActivity"

class SearchActivity : BaseActivity() {
  private var searchView: SearchView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    Log.d(TAG, "onCreate: ")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    activateToolbar(true)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    Log.d(TAG, "onCreateOptionsMenu: ")
    menuInflater.inflate(R.menu.menu_search, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
    val searchableInfo = searchManager.getSearchableInfo(componentName)
    searchView?.setSearchableInfo(searchableInfo)
    Log.d(TAG, "onCreateOptionsMenu: component [$componentName]")
    Log.d(TAG, "onCreateOptionsMenu: hint [${searchView?.queryHint}]")
    Log.d(TAG, "onCreateOptionsMenu: searchableInfo [${searchableInfo}]")

    searchView?.isIconified = false
    Log.d(TAG, "onCreateOptionsMenu: done")
    return true
  }
}