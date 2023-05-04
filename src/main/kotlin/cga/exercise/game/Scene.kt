package cga.exercise.game

import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.GameWindow
import org.lwjgl.opengl.GL30.*

/**
 * Created 29.03.2023.
 */
class Scene(private val window: GameWindow) {
    private val staticShader: ShaderProgram = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")

    //Deklaration des Hausmeshes
    private val assignment1mesh : Mesh



    //scene setup
    init {

        //initial opengl state
        glClearColor(0.0f, 0.533f, 1.0f, 1.0f); GLError.checkThrow()
        //1.2.5 Backface culling, GL_CCW = Gegen den Uhrzeitgersinn, GL_BACK = Face cullied
        enableFaceCulling(GL_CCW, GL_BACK)

        //HausVertices
        val vertices = floatArrayOf(
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f
        )

        //HausIndizes
        val indices = intArrayOf(
            0, 1, 2,
            0, 2, 4,
            4, 2, 3
        )

        /*InitialenVertices/////////////////////////////////////////////////////////////////////////////////////////////
        val vertices = floatArrayOf(

            //C
            -0.1f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            -0.7f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            -1.0f, -0.7f, 0.0f, 0.0f, 1.0f, 0.0f,
            -1.0f, 0.7f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.7f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            -0.1f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            //D
            0.7f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.7f, 0.0f, 0.0f, 0.0f, 1.0f,
            1.0f, -0.7f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.7f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            0.1f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.1f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
        )
        //InitialenIndizes//////////////////////////////////////////////////////////////////////////////////////////////
        val indices = intArrayOf(

            //C:
            0,1,2,3,4,5,
            //D:
            6,7,8,9,10,11

        )

         */

        //Erstelle das Array aus den Vertexattributen bestehen aus Informationen über den Aufbau
        val vertexarray= arrayOf(
            VertexAttribute(3,GL_FLOAT,24,0),
            VertexAttribute(3,GL_FLOAT,24,12),
        )




        //Kreiere das Mesh, das am Ende gerendert werden soll
        assignment1mesh = Mesh(vertexdata = vertices, indexdata = indices, attributes = vertexarray)






    }


    fun render(dt: Float, t: Float) {



        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        staticShader.use()
        assignment1mesh.render()



    }

    fun update(dt: Float, t: Float) {}

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {}

    fun cleanup() {}

    /**
     * enables culling of specified faces
     * orientation: ordering of the vertices to define the front face
     * faceToCull: specifies the face, that will be culled (back, front)
     * Please read the docs for accepted parameters
     */
    fun enableFaceCulling(orientation: Int, faceToCull: Int){
        glEnable(GL_CULL_FACE); GLError.checkThrow()
        glFrontFace(orientation); GLError.checkThrow()
        glCullFace(faceToCull); GLError.checkThrow()
    }

    /**
     * enables depth test
     * comparisonSpecs: specifies the comparison that takes place during the depth buffer test
     * Please read the docs for accepted parameters
     */
    fun enableDepthTest(comparisonSpecs: Int){
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(comparisonSpecs); GLError.checkThrow()
    }
}