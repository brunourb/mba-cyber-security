#!/usr/bin/env groovy

pipeline {
    //Agent é o NÓ que vai rodar o job
    agent any

    //Fases do pipeline
    stages {
        
       stage('build') {
            steps {
                script {
                    echo 'fazendo o build do projeto'
                }
            }
        }
        
       stage('unit-test') {
            steps {
                script {
                    echo 'fazendo o test do projeto'
                }
            }
        }
        
       stage('deploy to stage') {
            steps {
                script {
                    echo 'fazendo o deploy to stage do projeto'
                }
            }
        }
        
       stage('acceptance test') {
            steps {
                script {
                    echo 'fazendo o acceptance test do projeto'
                }
            }
        }
        
       stage('deploy to production') {
            steps {
                script {
                    echo 'fazendo o deploy to production do projeto'
                }
            }
        }         
        
     }
}
