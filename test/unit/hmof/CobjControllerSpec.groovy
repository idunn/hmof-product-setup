package hmof



import grails.test.mixin.*
import spock.lang.*

@TestFor(CobjController)
@Mock(Cobj)
class CobjControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.cobjInstanceList
            model.cobjInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.cobjInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def cobj = new Cobj()
            cobj.validate()
            controller.save(cobj)

        then:"The create view is rendered again with the correct model"
            model.cobjInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            cobj = new Cobj(params)

            controller.save(cobj)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/cobj/show/1'
            controller.flash.message != null
            Cobj.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def cobj = new Cobj(params)
            controller.show(cobj)

        then:"A model is populated containing the domain instance"
            model.cobjInstance == cobj
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def cobj = new Cobj(params)
            controller.edit(cobj)

        then:"A model is populated containing the domain instance"
            model.cobjInstance == cobj
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/cobj/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def cobj = new Cobj()
            cobj.validate()
            controller.update(cobj)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.cobjInstance == cobj

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            cobj = new Cobj(params).save(flush: true)
            controller.update(cobj)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/cobj/show/$cobj.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/cobj/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def cobj = new Cobj(params).save(flush: true)

        then:"It exists"
            Cobj.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(cobj)

        then:"The instance is deleted"
            Cobj.count() == 0
            response.redirectedUrl == '/cobj/index'
            flash.message != null
    }
}
