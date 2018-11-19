//package io.universalwatch.universalwatchapplication
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.provider.BaseColumns
//import com.universalwatch.uwlib.*
//import com.universalwatch.uwlib.WatchUtils.decodeBase64
//import com.universalwatch.uwlib.WatchUtils.saveBitmap
//import kotlinx.coroutines.experimental.launch
//
//object NotesApplication:ApplicationSingleton(){
//
//    private data class Note(
//            var title:String,
//            var content:String){
//        companion object {
//            fun getTitles():MutableList<String>{
//                val l = mutableListOf<String>()
//                for (note in notes){
//                    l.add(note.title)
//                }
//                return l
//            }
//            fun getContent():MutableList<String>{
//                val l = mutableListOf<String>()
//                for (note in notes){
//                    l.add(note.content)
//                }
//                return l
//            }
//        }
//
//    }
//    private val notes = mutableListOf(Note("Read a book...", "named Very short introductions"), Note("shopping list", "milk, egg, candies"), Note("lorem ipsum", "no idea"))
//    fun getListScreen(context: Context):ListView{
//
//        val listView  = ListView("list", simpleElements = Note.getTitles(), clickable = true)
//        listView.onClick = {c,id,extra->
//            val newView = getSingleScreen(context, id)
//            app!!.showView(context,newView)
//        }
//        listView.systemCallbacks.onBack = {c,extras->
//            app!!.close(context)
//            app==null
//            wasInitialized=false
//        }
//        return listView
//    }
//    fun getSingleScreen(context: Context, id:Int):TextView{
//        val actions = mutableListOf<Action>(
//                Action({extra->
//                    val id = extra.getString()!!.toInt()
//                    notes.removeAt(id)
//                    app!!.showView(context, getListScreen(context))
//                }, "delete",  id.toString())
//        )
//        val noteView = TextView("note", "Note", notes[id].title, actions, style = TextView.Companion.Layouts.TEXT_WALL_STYLE, onBack = { c, s->
//            val newView = getListScreen(context)
//            app!!.showView(context,newView)
//        })
//        return noteView
//
//    }
//    override fun createApplication(context: Context){
//        app = Application(context, "Notes", mutableListOf(), saveBitmap(context, decodeBase64("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAMDAwMDAwQEBAQFBQUFBQcHBgYHBwsICQgJCAsRCwwLCwwLEQ8SDw4PEg8bFRMTFRsfGhkaHyYiIiYwLTA+PlQBAwMDAwMDBAQEBAUFBQUFBwcGBgcHCwgJCAkICxELDAsLDAsRDxIPDg8SDxsVExMVGx8aGRofJiIiJjAtMD4+VP/CABEIAEAAQAMBIgACEQEDEQH/xAAdAAABBAMBAQAAAAAAAAAAAAAAAgMFCAEEBgcJ/9oACAEBAAAAAPqFVWOMEjaqmdCMGRV7s1AdWC7bW65yYWoN3U5qWdWEtHcvLOumZGO56YWtS3jw51axz2H/xAAYAQEAAwEAAAAAAAAAAAAAAAAEAwUGAP/aAAgBAhAAAAAZuTmBzMzxpnrqJ7n/xAAZAQADAQEBAAAAAAAAAAAAAAAAAgMEAQX/2gAIAQMQAAAA9HSZlcjXqzHI/wD/xABFEAABAgMDBQoJCwUBAAAAAAACAwQAAQUGERIHExRBcQgVGCEjMlJh0dIzVFZXYpOUltMWIjFCQ0RRZHOhoiQldIKDkv/aAAgBAQABPwCp1OnUWnOKg/cptmrZMlFllCwgAD9M5ziq7rezKDxROl0J8/QkV0lyUBvI+sRnfOOGAw8knPtodyOGAw8knPtodyOF+w8knPtodyOF+w8knPtodyKXuuLMrvE06lQnzFAiumuKgOMHWQyunFLqtNrtMa1CmuU3LRykKiK6ZYgUAtcpxuzrTu2FJsrREliBvUHThw5HUrJrIcA7MR3xvt6Ub7RvtG+3pRvt6Ub7elG4wtM8fNbWUNRUjbslmrpuEy4ktIkQmI7SC+N19YC11u6lY2Vn0UFZtEn81s4Uxw483hgtz3leFQQ0NjeWH7wev/WB3OeWhTwVNZnsWPuxwcctQ/TS2Y/9D7scG/LUXMpbUtih92ODblu5u87XZnDgdzTlyLm0Vntm4ujclZLLe5OazaxW07NBuD5qzk3za2dvJIzxxWs5pieAUiHNcePbsnCyjrTG8pJoD4P60+nsjOPC+ql6yfZGcedFK/8AUn2RnHhfZpbc5PsjE+5pCl6yfZGJ4P1Uv/U+yKcSxOyzghLkuK4plritiJPEb3BJclqKQ4uPrhwKemN5C8VLwf2gdPZApjrfK+uDsjNp86b5W/8AUDsjCnqeK+sDsjCnzZPFfWB2RhHxxXbnA7Io4BpxcsavJayGevqi0E0hepCTcl+S4sKeO7jh0o3F43/t6v2f3eXTgVG+qnq3/wCPKBJr9Ogql1aPKMTXxFX2eUYmviKu3R5RiR8TV9nlFKmlpZ4ECS5LWngv44r7pRCps24GhInCCmDOX8ebumUhu13TvhwT7TG+HQ7uT6fTgd8NWh7PnwJPvyvX4SB04fFf5x/Xflf5wJPh5ui/zikOyOquEJkkRoIJzPN3zwZ2fzZFf+Mhi1tl2trqQowVXXaLSIVWr1AsKzVcOYqnP8R/eUOW2XijrgDmzdMtLmcMhfMqtOnzWkM+cqgqByA5+jO6N/MtXmxL3oR+FG/+WrzXl70I/Cj5QZafNeXvQj8KPlBlp815e9CPwokvl4rU9Ga2VplnMXPfvqxOoZoekDdIAxl1FO6LIWWb2SpItJOV3rpUs69fr3TWdLlzlDu/YZcUpR//xAAsEQABAwIBCgcBAAAAAAAAAAACAAMEBRIBExUhJTEyUnGCohFBQlNUsbLR/9oACAECAQE/AH3TwO0VlZHEstJ40w6d1paVUq1IjVF1kRG0bdvJBXZZ+htZ6l8LaptVkvzmmyFu0vHd5KuHrmT0/lMkjNUc9ZsdX0q5QJcmaUqOInfbcOzHDHBBQqz8buH+rMlW9juFUajSWZAyJAiNu6PnpX//xAAhEQABBAEEAwEAAAAAAAAAAAACAAMEElIRExQhECIyov/aAAgBAwEBPwCJHZMLOd6rjQ8P0uNDxUqOyAWb60Ue9B9lY8lY8k6R0L2Uf4FH4d+SUeQABUlus5LdZyTroVqK/9k=")))
//        app!!.initialView = getListScreen(context)
//    }
//}
//class NotesApplicationRuntime:ApplicationRuntime(){
//    override fun makeApplication(context: Context): Application {
//        return NotesApplication.getApplication(context)
//    }
//}