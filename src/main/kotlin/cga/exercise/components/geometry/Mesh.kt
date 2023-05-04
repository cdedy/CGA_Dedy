package cga.exercise.components.geometry

import cga.framework.GLError
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*

/**
 * Creates a Mesh object from vertexdata, intexdata and a given set of vertex attributes
 *f
 * @param vertexdata plain float array of vertex data
 * @param indexdata  index data
 * @param attributes vertex attributes contained in vertex data
 * @throws Exception If the creation of the required OpenGL objects fails, an exception is thrown
 *
 * Created 29.03.2023.
 */
class Mesh(vertexdata: FloatArray, indexdata: IntArray, attributes: Array<VertexAttribute>) {
    //private data
    private var vaoId = 0
    private var vboId = 0
    private var iboId = 0
    private var indexcount = 0

    init{

    // todo Aufgabe 1.2.2 (shovel geometry data to GPU and tell OpenGL how to interpret it)

    //generieren : Erstellen und Binden eines VAO. Jedes VBO und IBO, die Sie in der Sequenz binden, wird dem aktuellen VAO zugeordnet.
    vaoId = glGenVertexArrays() //Vertex Array Object = speichert Konfiguration der Vertex-Attribute / Wie werden Daten auf der Grafikkarte gespeichert?
    vboId = glGenBuffers() //Vertex Buffer Object = speichert Vertex-Daten / Daten für die Grafikkarte
    iboId = glGenBuffers() //Index Buffer Object = speichert Index-Daten (Reihenfolge der Vertexdaten)

    //binden : Erstellen und Binden von Vertex- und Indexbuffern.
    glBindVertexArray(vaoId)
    glBindBuffer(GL_ARRAY_BUFFER, vboId) //Der Buffer mit den 'echten' Daten
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId) //Der Buffer für Indizes (Reihenfolge der Vertices)


    //fuellen : Füllen Sie die Vertex- und Indexbuffer mit den entsprechenden Daten.
    glBufferData(GL_ARRAY_BUFFER, vertexdata, GL_STATIC_DRAW)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)


    //vertex : Aktivieren und definieren Sie die jeweiligen VertexAttribute.
    indexcount = indexdata.size
    for (i in attributes.indices)
    {
        //aktiviere das aktuelle Attribut aus der Liste der Attribute
        glEnableVertexAttribArray(i)
        //erkäre OpenGL wo/wie die Daten liegen
        glVertexAttribPointer(
            i, attributes[i].n, attributes[i].type,
            false, attributes[i].stride, (attributes[i].offset).toLong()
        )
    }

    //unbinden : Danach sollte alles gelöst werden (unbind), um versehentliche Änderungen an den Buffern und VAO zu vermeiden. Denken Sie daran, das VAO zuerst zu lösen.
    glBindVertexArray(0)

    //Renderaufruf
    render()

}





    /**
     * Renders the mesh
     */
    fun render() {
        //rendern : Binden Sie das VAO.
        glBindVertexArray(vaoId)
        //Zeichnen Sie die Elemente.

        glDrawElements(GL_TRIANGLES,indexcount, GL_UNSIGNED_INT, 0)
        //((glDrawElements(GL_LINE_STRIP,indexcount, GL_UNSIGNED_INT, 0)///////////////////////////////////////////

        //Lösen der Bindung, um versehentliche Änderungen am VAO zu vermeiden.
        glBindVertexArray(0)



    }

    /**
     * Deletes the previously allocated OpenGL objects for this mesh
     */
    fun cleanup() {
        if (iboId != 0) glDeleteBuffers(iboId)
        if (vboId != 0) glDeleteBuffers(vboId)
        if (vaoId != 0) glDeleteVertexArrays(vaoId)
    }
}