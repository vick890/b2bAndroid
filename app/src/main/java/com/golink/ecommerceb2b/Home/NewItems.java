package com.golink.ecommerceb2b.Home;

public class NewItems {

        private String id;
        private String name;
        private String location;
        private String image;


        public NewItems(String id, String name, String lobuscation, String image) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getImage() {
            return image;
        }

}
