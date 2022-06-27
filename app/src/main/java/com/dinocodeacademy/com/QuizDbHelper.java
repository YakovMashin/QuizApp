package com.dinocodeacademy.com;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dinocodeacademy.com.QuizContract.QuestionTable;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

   private static final String DATABASE_NAME = "GoQuiz.db";
   private static final int DATABASE_VERSION = 3;

    private SQLiteDatabase db;


    QuizDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // init database and create table based on Quiz Contract
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionTable.COLUMN_CATEGORY + " TEXT, " +
                QuestionTable.COLUMN_LEVELS_ID + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE); // create the table
        fillQuestionsTable(); // add questions to the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // if upgraded drop the last table and create the new one
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }


    private void fillQuestionsTable()
    {

        // create the questions based on Questions class
// Technology Questions
        // Level 1
        Questions q1 = new Questions("Android is what ?", "OS", "Drivers", "Software", "Hardware", 1,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_1);
        addQuestions(q1);

        Questions q2 = new Questions("Full form of PC is ?", "OS", "Personal Computer", "Pocket Computer", "Hardware", 2,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_1);
        addQuestions(q2);

        Questions q3 = new Questions("Windows is what ?", "Easy Software", "Hardware Device", "Operating System", "Hardware", 3,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_1);
        addQuestions(q3);

        Questions q4 = new Questions("Unity is used for what ?", "Game Development", "Movie Making", "Firmware", "Hardware", 1,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_1);
        addQuestions(q4);

        Questions q5 = new Questions("RAM stands for ", "Windows", "Drivers", "GUI", "Random Access Memory", 4,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_1);
        addQuestions(q5);

        Questions q6 = new Questions("Chrome is what ?", "OS", "Browser", "Tool", "New Browser", 2,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_1);
        addQuestions(q6);

        //Level 2
        Questions q19 = new Questions("What is Bios?", "A piece of computer hardware", "Basic settings of the computer", "A firmware to provide runtime services", "None of the above", 3,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_2);
        addQuestions(q19);

        //Level 3
        Questions q20 = new Questions("In what year the first PC was released?", "1983", "1979", "1981", "1987", 3,
                Questions.CATEGORY_TECHNOLOGY,
                Questions.LEVEL_3);
        addQuestions(q20);


//Programming Questions
        // Level 1
        Questions q7 = new Questions("What does the command Console.ReadKey does?", "getting user input", "stop programme execution until a key is pressed", "generating ReadKey element", "none of the above", 2,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);
        addQuestions(q7);

        Questions q8 = new Questions("which of the following are value types in c#?", "int32", "decimal", "double", "all of the above", 4,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);

        addQuestions(q8);
        Questions q9 = new Questions("How do you insert comments to c# code??", "// this is a comment ", "/ this is a comment", "# this is a comment", "None of the above", 1,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);

        addQuestions(q9);

        Questions q10= new Questions("Which is the correct way to create a new line?", "/n", "Console.CreateLine", "Console.CreateNewLine", "/t", 1,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);
        addQuestions(q10);

        Questions q11 = new Questions("What is the right search order in inorder search (סריקה תחילית)", "Root, Left, Right", "Left, Root, Right", "Right, Root,Left", "Left,Right, Root", 4,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);

        addQuestions(q11);
        Questions q12 = new Questions("What is casting in c#?", "idk", "Changing variable modifier", "Changing value of the variable", "Converting one datatype to another", 4,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);

        addQuestions(q12);
        Questions q13 = new Questions("What is the correct way to create an object called myObj of MyClass?", "MyClass myClass = new MyClass();", "MyClass myClass = new object();", "new obj = new MyClass()", "class MyClass = new MyClass();", 1,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);
        addQuestions(q13);

        Questions q14 = new Questions("Which access modifier makes the code only accessible within the same class?", "private", "public", "protected", "Readonly", 1,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1
        );
        addQuestions(q14);
        Questions q15 = new Questions("What does the following method does? bool func(num)" +
                "for (i = 1; i <= Math.Sqrt(num); i++) if (num % i == 0) return false;" +
                "else return true;", "Checks if the num is prime", "Checks if the num is perfect number", "checks if the num is natural number", "none of the above", 1,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);
        addQuestions(q15);

        Questions q16 = new Questions("What are the correct braces to declare an array", "{}", "()", "[]", "Console.CreateArray()", 3,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_1);
        addQuestions(q16);

        // Level 2
        Questions q21 = new Questions("A hard programming question", "Wrong answer", "Right answer", "Wrong", "Maybe? (nah)", 2,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_2);
        addQuestions(q21);

        //Level 3
        Questions q22 = new Questions("A damn hard question", "Wrong answer", "Right answer", "Wrong", "Maybe? (nah)", 2,
                Questions.CATEGORY_PROGRAMMING,
                Questions.LEVEL_3);
        addQuestions(q22);

// History Questions
        //Level 1
        Questions q17 = new Questions("When did the World war 2 start?", "1941", "1939", "1914", "1945", 2,
                Questions.CATEGORY_HISTORY,
                Questions.LEVEL_1);
        addQuestions(q17);
        Questions q18 = new Questions("What was the name of Israel's first prime minister?", "Fred Ganopolsky", "Heim Weizman", "Ben Gurion", "Golda Meir", 3,
                Questions.CATEGORY_HISTORY,
                Questions.LEVEL_1);
        addQuestions(q18);

        //Level 2
        Questions q23 = new Questions("When did the battle of Stalingrad started?", "1942", "1943", "1941", "1944", 1,
                Questions.CATEGORY_HISTORY,
                Questions.LEVEL_2);
        addQuestions(q23);

        //Level 3
        Questions q24 = new Questions("Who was the first class teacher of Yud-ber Lizei?", "Yegor Tsyganok", "Yakov Jerbi", "More Rotem", "More Gil", 4,
                Questions.CATEGORY_HISTORY,
                Questions.LEVEL_3);
        addQuestions(q24);

    }

    // add the question to the table
    private void addQuestions(Questions question){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        cv.put(QuestionTable.COLUMN_CATEGORY, question.getCategory());
        cv.put(QuestionTable.COLUMN_LEVELS_ID, question.getLevels());
        db.insert(QuestionTable.TABLE_NAME,null,cv);

    }
    @SuppressLint("Range")
    // get the questions based on category and level and return arraylist
    public ArrayList<Questions> getLevelAndCategoryQuestions(int level, String category) {

        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();



        String[] Projection = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR,
                QuestionTable.COLUMN_CATEGORY,
                QuestionTable.COLUMN_LEVELS_ID
        };


        // selection args for the query
        String selection = QuestionTable.COLUMN_LEVELS_ID + " = ? " +
                " AND " + QuestionTable.COLUMN_CATEGORY + " = ? ";
        String[] selectionArgs = {String.valueOf(level),category};
        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        // get all the properties
        if (c.moveToFirst()) {
            do {

                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionTable.COLUMN_CATEGORY)));
                question.setLevels(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_LEVELS_ID)));
                questionList.add(question);

            } while (c.moveToNext());

        }
        c.close();
        return questionList;
    }

}


