package contactBook;

import java.util.HashSet;
import java.util.Set;

public class ContactBook {
    static final int DEFAULT_SIZE = 100;

    private int counter;
    private Contact[] contacts;
    private int currentContact;
    private final Set<Integer> phoneNumbers;

    public ContactBook() {
        counter = 0;
        contacts = new Contact[DEFAULT_SIZE];
        currentContact = -1;
        phoneNumbers = new HashSet<>(DEFAULT_SIZE);
    }

    //Pre: name != null
    public boolean hasContact(String name) {
        return searchIndex(name) >= 0;
    }

    public int getNumberOfContacts() {
        return counter;
    }

    //Pre: name!= null && !hasContact(name)
    public void addContact(String name, int phone, String email) {
        if (counter == contacts.length)
            resize();
        contacts[counter] = new Contact(name, phone, email);
        counter++;
        phoneNumbers.add(phone);
    }

    //Pre: name != null && hasContact(name)
    public void deleteContact(String name) {
        int index = searchIndex(name);
        int phone = getPhone(name);
        for(int i=index; i<counter; i++)
            contacts[i] = contacts[i+1];
        counter--;
        if (getContactFromPhone(phone) == null) phoneNumbers.remove(phone);
    }

    //Pre: name != null && hasContact(name)
    public int getPhone(String name) {
        return contacts[searchIndex(name)].getPhone();
    }

    //Pre: name != null && hasContact(name)
    public String getEmail(String name) {
        return contacts[searchIndex(name)].getEmail();
    }

    //Pre: name != null && hasContact(name)
    public void setPhone(String name, int phone) {
        phoneNumbers.add(phone);
        Contact c = contacts[searchIndex(name)];
        int oldPhone = c.getPhone();
        c.setPhone(phone);
        if (getContactFromPhone(oldPhone) == null) phoneNumbers.remove(oldPhone);
    }

    //Pre: name != null && hasContact(name)
    public void setEmail(String name, String email) {
        contacts[searchIndex(name)].setEmail(email);
    }

    private int searchIndex(String name) {
        int i = 0;
        int result = -1;
        boolean found = false;
        while (i<counter && !found)
            if (contacts[i].getName().equals(name))
                found = true;
            else
                i++;
        if (found) result = i;
        return result;
    }

    private void resize() {
        Contact tmp[] = new Contact[2*contacts.length];
        for (int i=0;i<counter; i++)
            tmp[i] = contacts[i];
        contacts = tmp;
    }

    public void initializeIterator() {
        currentContact = 0;
    }

    public boolean hasNext() {
        return (currentContact >= 0 ) && (currentContact < counter);
    }

    //Pre: hasNext()
    public Contact next() {
        return contacts[currentContact++];
    }

    // returns the contact that has [phone] as its phone number, null if there isn't one
    public Contact getContactFromPhone(int phone) {
        this.initializeIterator();
        while( this.hasNext() ) {
            Contact c = this.next();
            if (c.getPhone() == phone) return c;
        }
        return null;
    }

    // checks if there are at least two equal phone numbers
    public boolean differentNumbers() {
            return phoneNumbers.size() == counter;
    }
}
