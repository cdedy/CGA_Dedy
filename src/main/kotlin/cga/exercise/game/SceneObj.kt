package cga.exercise.game

import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.OBJLoader
import cga.framework.GameWindow
import org.lwjgl.opengl.GL30.*

/**
 * Created 29.03.2023.
 */
class SceneObj(private val window: GameWindow) {
    private val staticShader: ShaderProgram = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")

    //Meshes die am Ende zu rendern sind
    private val meshListe = mutableListOf<Mesh>()


    //scene setup
    init {
        //BackFaceCulling aktivieren ( Flächen werden ausgeblendet um unnötige Berechnungen zu vermeiden (welche Flächen sind sichtbar und welche nicht) CCW = gegen Uhrzeigersinn
        enableFaceCulling(GL_CCW,GL_BACK)
        //GL_LESS = Tiefenwert kleiner als gespeicherter Tiefenwert
        enableDepthTest(GL_LESS)

        //Hintergrund schwärzen
        glClearColor (0.0f , 0.0f , 0.0f , 1.0f); GLError . checkThrow ()



        //loadObj() erstellt ein OBJResult

        //OBJLoader generiert über seine "loadOBJ" Funktion anhand von Pfad ein Result-Objekt mit allen enthaltenen Meshes
        val objektSphaere : OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/models/sphere.obj")
        //Liste erstellen mit allen Meshes des Objekts
        val objMeshList: MutableList<OBJLoader.OBJMesh> = objektSphaere.objects[0].meshes
        //Definieren Sie die VertexAttributes...
        val sphaere_pos = VertexAttribute(3, GL_FLOAT, 32,0)
        val sphaere_tex = VertexAttribute(2, GL_FLOAT, 32, 12)
        val sphaere_norm = VertexAttribute(3, GL_FLOAT, 32, 20)
        //...und erzeugen Sie die Mesh-Objekte
        val sphaerenattribute = arrayOf(sphaere_pos, sphaere_tex, sphaere_norm)



        //Rendervorbereitung
        //Für die Meshinformationen unserer Sphäre
        for (mesh in objMeshList) {
            //wird nun ein allgemeines renderbares Meshobjekt erzeugt
            val sphaerenmesh = Mesh(vertexdata = mesh.vertexData, indexdata = mesh.indexData, attributes = sphaerenattribute)
            //und zu der Liste der zu rendernden Meshobjekte hinzugefügt
            meshListe.add(sphaerenmesh)

        }



    }


    fun render(dt: Float, t: Float) {

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        staticShader.use()

        //Jedes Mesh in der Liste der am Ende zu rendernden Objekte..
        for(mesh in meshListe)
        {
            //wird nun gerendert
            mesh.render()
        }
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