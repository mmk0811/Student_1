package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Mai Minh Khôi", "001"),
    StudentModel("Bùi Nguyễn Xuân Thành", "002"),
    StudentModel("Bùi Mạnh Dũng", "003"),
    StudentModel("Phạm Đức Lưu", "004"),
    StudentModel("Phạm Thái Châu", "005"),
    StudentModel("Phạm Hải Nam", "006"),
    StudentModel("Trịnh Châu", "007"),
    StudentModel("Phạm Thị Phương Hoa", "008"),
    StudentModel("Đinh Văn Luận", "009"),
    StudentModel("Nguyễn Văn Tuyến", "010"),
    StudentModel("Ninh Văn Vươngg", "011"),
    StudentModel("Nguyễn Quang Thịnh", "012"),
    StudentModel("Phan Văn Đức", "013"),
    StudentModel("Nguyễn Công Phượng", "014"),
    StudentModel("Tà Phạm Thuật", "015"),
    StudentModel("Nguyễn Phúc Mạnh", "016"),
    StudentModel("Nguyễn Đức Dương", "017"),
    StudentModel("Trần Quang Thắng", "018"),
    StudentModel("Vũ Trung Kiên", "019"),
    StudentModel("Trần Xuân Thuận", "020")
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val btnAddNew = findViewById<Button>(R.id.btn_add_new)
    studentAdapter = StudentAdapter(
      students,
      onEdit = { student: StudentModel -> showEditStudentDialog(student) },
      onDelete = { student: StudentModel -> confirmDeleteStudent(student) }
    )

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    btnAddNew.setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
    val studentNameEditText = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val studentIdEditText = dialogView.findViewById<EditText>(R.id.edit_student_id)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = studentNameEditText.text.toString()
        val id = studentIdEditText.text.toString()

        if (name.isNotEmpty() && id.isNotEmpty()) {
          val newStudent = StudentModel(name, id)
          students.add(newStudent)
          studentAdapter.notifyItemInserted(students.size - 1)
          Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
        }
      }
      .setNegativeButton("Cancel", null)
      .create()

    dialog.show()
  }

  private fun showEditStudentDialog(student: StudentModel) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
    val nameInput = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val idInput = dialogView.findViewById<EditText>(R.id.edit_student_id)

    // Hiển thị tên và mã sinh viên hiện tại trong dialog
    nameInput.setText(student.studentName)
    idInput.setText(student.studentId)

    AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Update") { _, _ ->
        val newName = nameInput.text.toString()
        val newId = idInput.text.toString()

        if (newName.isNotEmpty() && newId.isNotEmpty()) {
          student.studentName = newName  // Cập nhật tên sinh viên
          student.studentId = newId     // Cập nhật mã sinh viên
          studentAdapter.notifyDataSetChanged()
        } else {
          Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
      }
      .setNegativeButton("Cancel", null)
      .show()
  }



  private fun confirmDeleteStudent(student: StudentModel) {
    AlertDialog.Builder(this)
      .setMessage("Are you sure you want to delete this student?")
      .setPositiveButton("Yes") { _, _ ->
        deleteStudent(student)
      }
      .setNegativeButton("No", null)
      .show()
  }

  private fun deleteStudent(student: StudentModel) {
    val position = students.indexOf(student)
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    Snackbar.make(findViewById(R.id.recycler_view_students), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, student)
        studentAdapter.notifyItemInserted(position)
      }
      .show()
  }
}
