import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

// Device interface
interface Device {
    void turnOn();
    void turnOff();
    String getStatus();
}

// Concrete Light class
class Light implements Device {
    private int id;
    private String status;

    public Light(int id) {
        this.id = id;
        this.status = "off";
    }

    @Override
    public void turnOn() {
        this.status = "on";
    }

    @Override
    public void turnOff() {
        this.status = "off";
    }

    @Override
    public String getStatus() {
        return "Light " + id + " is " + status + ".";
    }
}

// Concrete Thermostat class
class Thermostat implements Device {
    private int id;
    private int temperature;

    public Thermostat(int id, int temperature) {
        this.id = id;
        this.temperature = temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public void turnOn() {
        // Thermostat doesn't have an on/off state
    }

    @Override
    public void turnOff() {
        // Thermostat doesn't have an on/off state
    }

    @Override
    public String getStatus() {
        return "Thermostat is set to " + temperature + " degrees.";
    }
}

// Concrete Door class
class Door implements Device {
    private int id;
    private String status;

    public Door(int id) {
        this.id = id;
        this.status = "locked";
    }

    @Override
    public void turnOn() {
        // Door doesn't have an on/off state
    }

    @Override
    public void turnOff() {
        // Door doesn't have an on/off state
    }

    @Override
    public String getStatus() {
        return "Door is " + status + ".";
    }
}

// Device Factory
class DeviceFactory {
    public static Device createDevice(int id, String type, int temperature) {
        switch (type) {
            case "light":
                return new Light(id);
            case "thermostat":
                return new Thermostat(id, temperature);
            case "door":
                return new Door(id);
            default:
                throw new IllegalArgumentException("Invalid device type");
        }
    }
}

// Proxy for Device control (Optional)
class DeviceProxy implements Device {
    private Device device;

    public DeviceProxy(Device device) {
        this.device = device;
    }

    @Override
    public void turnOn() {
        device.turnOn();
    }

    @Override
    public void turnOff() {
        device.turnOff();
    }

    @Override
    public String getStatus() {
        return device.getStatus();
    }
}

// Smart Home System
class SmartHomeSystem {
    private Map<Integer, Device> devices = new HashMap<>();
    private int deviceIdCounter = 1;
    
    public int getNextDeviceId() {
        return deviceIdCounter++;
    }

    public void addDevice(Device device) {
        devices.put(deviceIdCounter++, device);
    }

    public void turnOn(int deviceId) {
        Device device = devices.get(deviceId);
        if (device != null) {
            device.turnOn();
        } else {
            System.out.println("Device not found.");
        }
    }

    public void turnOff(int deviceId) {
        Device device = devices.get(deviceId);
        if (device != null) {
            device.turnOff();
        } else {
            System.out.println("Device not found.");
        }
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder();
        for (Map.Entry<Integer, Device> entry : devices.entrySet()) {
            status.append("Device ").append(entry.getKey()).append(": ").append(entry.getValue().getStatus()).append(" ");
        }
        return status.toString().trim();
    }

    public Map<Integer, Device> getDevices() {
        return devices;
    }
}

public class SmartHomeSimulation {
    public static void main(String[] args) {
        // Initialize Smart Home System
        SmartHomeSystem smartHome = new SmartHomeSystem();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Smart Home System Menu ---");
            System.out.println("1. Add Light");
            System.out.println("2. Turn On Device");
            System.out.println("3. Turn Off Device");
            System.out.println("4. Get Status Report");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Device light = DeviceFactory.createDevice(smartHome.getNextDeviceId(), "light", 0);
                    smartHome.addDevice(light);
                    System.out.println("Light added with ID: " + smartHome.getNextDeviceId());
                    break;
                case 2:
                    System.out.print("Enter device ID to turn on: ");
                    int deviceIdOn = scanner.nextInt();
                    smartHome.turnOn(deviceIdOn);
                    break;
                case 3:
                    System.out.print("Enter device ID to turn off: ");
                    int deviceIdOff = scanner.nextInt();
                    smartHome.turnOff(deviceIdOff);
                    break;
                case 4:
                    System.out.println(smartHome.getStatus());
                    break;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}