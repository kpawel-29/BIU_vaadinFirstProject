package kalk;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("mytheme")
@Widgetset("kalk.MyAppWidgetset")
public class MyUI extends UI {

	public int oblicz(int a, int b, String s) {
		switch (s) {
		case "+":  
			return a+b;
		case "-":  
			return a-b;
		case "*":  
			return a*b;
		case "/":  
			return a/b;
		default:
			return 0;
			}
	}
	
	NativeSelect select = new NativeSelect();
	int a,b;
	String s = "+";
	

	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        
        TextField txtFirst = new TextField();
        txtFirst.setInputPrompt("Liczba");
        txtFirst.setMaxLength(10);
        txtFirst.setImmediate(true);
        
        TextField txtSecond = new TextField();
        txtSecond.setInputPrompt("Liczba");
        txtSecond.setMaxLength(10);
        
        final Button button = new Button("Oblicz");
        //button.setEnabled(false);
        
        select.addItem("+");
        select.addItem("-");
        select.addItem("*");
        select.addItem("/");
        
        select.setNullSelectionAllowed(false);
        select.setValue("+");
        select.setImmediate(true);
        
        layout.addComponent(txtFirst);
        layout.addComponent(select);
        layout.addComponent(txtSecond);
        
        select.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty().getValue());	
				s = valueString;
			}
		});
        
        txtFirst.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(final ValueChangeEvent event) {
                final String txtF = String.valueOf(event.getProperty().getValue());
                a = Integer.valueOf(txtF);
                //button.setEnabled(txtF.length() > 0);
            }
        });
        txtSecond.addValueChangeListener(new ValueChangeListener() {
            public void valueChange(final ValueChangeEvent event) {
                final String txtS = String.valueOf(event.getProperty().getValue());
                b = Integer.valueOf(txtS);
                //button.setEnabled(txtS.length() > 0);
            }
        });
        
        
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label(a + " " + s + " " + b + " = " + String.valueOf(oblicz(a,b,s))));
            }
        });
        layout.addComponent(button);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    	
    }

}
